package com.proxiad.hangmangame.logic.game;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.proxiad.hangmangame.logic.word.HangmanWordRepository;
import com.proxiad.hangmangame.model.GameSession;
import com.proxiad.hangmangame.model.HangmanWord;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThat;
import static com.proxiad.hangmangame.logic.game.GameConstants.SECRET_ENCODE_VAL;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceImplTest {

  @Mock private HangmanWordRepository wordRepository;

  @Mock private GameSessionDao gameSessionDao;

  @InjectMocks private GameSessionServiceImpl gameSessionService;

  @Test
  void startNewGameTest() {
    // given
    given(wordRepository.getWordById(anyInt())).willReturn(new HangmanWord("example"));

    // when
    GameSession newSession = gameSessionService.startNewGame();

    // then
    then(wordRepository).should().getWordById(anyInt());
    assertThat(newSession).isNotNull();
    assertThat(newSession.getWordToGuess()).isEqualTo("example");
  }

  @ParameterizedTest
  @MethodSource("supplyWordToGuessAndPuzzledWord")
  void testGameSessionInit(
      String word, String expectedPuzzledWord, int expectedCountOfHiddenLetters) {

    // given
    HangmanWord hangmanWord = new HangmanWord(word);
    given(wordRepository.getWordById(anyInt())).willReturn(hangmanWord);

    // when
    GameSession newSession = gameSessionService.startNewGame();
    String puzzledWord = newSession.getPuzzledWord();
    int lettersToGuessLeft = newSession.getLettersToGuessLeft();

    // then
    then(wordRepository).should().getWordById(anyInt());
    then(gameSessionDao).should().save(any(GameSession.class));
    assertThat(puzzledWord).isEqualTo(expectedPuzzledWord);
    assertThat(lettersToGuessLeft).isEqualTo(expectedCountOfHiddenLetters);
  }

  private static Stream<Arguments> supplyWordToGuessAndPuzzledWord() {
    return Stream.of(
        Arguments.of("stack", "s___k", 3),
        Arguments.of("kilowatt", "k_____tt", 5),
        Arguments.of("interface", "i__e____e", 6),
        Arguments.of("system", "s_s__m", 3));
  }

  @ParameterizedTest
  @MethodSource("supplyWordToGuessEnteredLetterAndHiddenLetterCount")
  void makeTryTest(String wordToGuess, String userInputLetter, int expectedHiddenLettersCount)
      throws ServletException {

    // given, when
    HangmanWord hangmanWord = new HangmanWord(wordToGuess);
    given(wordRepository.getWordById(anyInt())).willReturn(hangmanWord);
    GameSession newSession = gameSessionService.startNewGame();
    int intialTiresLeft = newSession.getTriesLeft();
    given(gameSessionDao.get(anyString())).willReturn(Optional.of(newSession));
    GameSession sessionAfterGuess = gameSessionService.makeTry(wordToGuess, userInputLetter);

    // then
    assertThat(sessionAfterGuess.getLettersToGuessLeft()).isEqualTo(expectedHiddenLettersCount);
    assertThat(sessionAfterGuess.getTriesLeft()).isEqualTo(intialTiresLeft - 1);
  }

  private static Stream<Arguments> supplyWordToGuessEnteredLetterAndHiddenLetterCount() {
    return Stream.of(
        Arguments.of("stack", "j", 3),
        Arguments.of("storm", "o", 2),
        Arguments.of("acceptance", "c ", 3));
  }

  @Test
  void repetitiveLetterEnteringTest() throws Exception {

    // given
    String exampleGameId = "A12BD13D";
    String wordToGuess = "interface";
    HangmanWord hangmanWord = new HangmanWord(wordToGuess);
    given(wordRepository.getWordById(anyInt())).willReturn(hangmanWord);
    GameSession gameSession = gameSessionService.startNewGame();

    int intialCountOfLettersToGuess = gameSession.getLettersToGuessLeft();
    String userInput = "e";
    int initialTriesLeft = gameSession.getTriesLeft();
    given(gameSessionDao.get(anyString())).willReturn(Optional.of(gameSession));

    // when
    GameSession newSession = gameSessionService.makeTry(exampleGameId, userInput);
    int lettersToGuessLeft = newSession.getLettersToGuessLeft();

    // then
    assertThat(lettersToGuessLeft).isEqualTo(intialCountOfLettersToGuess);
    assertThat(gameSession.getTriesLeft()).isLessThan(initialTriesLeft);
  }

  @Test
  void validationGameSessionTest() {

    String exampleGameId = "A12BD13D";
    String exampleUserGuess = "q";
    // given
    given(gameSessionDao.get(anyString())).willReturn(Optional.ofNullable(null));

    // when, then
    assertThrows(
        InvalidGameSessionException.class,
        () -> gameSessionService.makeTry(exampleGameId, exampleUserGuess));
  }

  @Test
  void encodeLettersToBeGuessedTest() {

    // given
    HangmanWord hangmanWord = new HangmanWord("interface");
    given(wordRepository.getWordById(anyInt())).willReturn(hangmanWord);

    GameSession gameSession = gameSessionService.startNewGame();
    String encodedLettersToGuess = gameSession.getLettersToBeGuessedEncoded();

    // when,then
    for (char encodedLetter : encodedLettersToGuess.toCharArray()) {
      char decodedLetter = (char) (encodedLetter + SECRET_ENCODE_VAL);
      assertThat(gameSession.getWordToGuess().contains(String.valueOf(decodedLetter))).isTrue();
    }
  }
}
