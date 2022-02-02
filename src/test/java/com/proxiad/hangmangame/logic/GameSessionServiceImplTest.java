package com.proxiad.hangmangame.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
import com.proxiad.hangmangame.model.GameSessionRepository;
import com.proxiad.hangmangame.model.WordRepository;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceImplTest {

  @Mock private WordRepository wordRepository;

  @Mock private GameSessionRepository gameSessionRepository;

  @InjectMocks private GameSessionServiceImpl gameSessionService;

  private String exampleGameId = "A12BD13D";
  private String exampleUserGuess = "z";

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void makeTryTest(String wordToGuess, String userInputLetter, int lettersToGuessLeft)
      throws ServletException {

    // given
    GameSession gameSession = new GameSession(wordToGuess);
    int initialTries = gameSession.getTriesLeft();
    given(gameSessionRepository.getGameSessionById(anyString()))
        .willReturn(Optional.of(gameSession));

    // when
    gameSessionService.makeTry(exampleGameId, userInputLetter);

    int receivedLettersToGuess = gameSession.getLettersToGuessLeft();

    // then
    assertThat(receivedLettersToGuess).isEqualTo(lettersToGuessLeft);
    assertThat(gameSession.getTriesLeft()).isEqualTo(initialTries - 1);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(
        Arguments.of("stack", "j", 3),
        Arguments.of("storm", "o", 2),
        Arguments.of("acceptance", "c ", 3));
  }

  @Test
  void repetitiveLetterEnteringTest() throws Exception {

    // given
    String wordToGuess = "interface";
    GameSession gameSession = new GameSession(wordToGuess);
    int previousLettersToGuessLeft = gameSession.getLettersToGuessLeft();
    String userInput = "e";
    int initialTriesLeft = gameSession.getTriesLeft();
    given(gameSessionRepository.getGameSessionById(anyString()))
        .willReturn(Optional.of(gameSession));

    // when
    GameSession newSession = gameSessionService.makeTry(exampleGameId, userInput);
    int lettersToGuessLeft = newSession.getLettersToGuessLeft();

    // then
    assertThat(lettersToGuessLeft).isEqualTo(previousLettersToGuessLeft);
    assertThat(gameSession.getTriesLeft()).isLessThan(initialTriesLeft);
  }

  @Test
  void startNewGameTest() {
    // given
    given(wordRepository.getWord()).willReturn("example");

    // when
    GameSession newSession = gameSessionService.startNewGame();

    // then
    then(wordRepository).should().getWord();
    assertThat(newSession).isNotNull();
    assertThat(newSession.getWordToGuess()).isEqualTo("example");
  }

  @Test
  void validationGameSessionTest() {

    // given
    given(gameSessionRepository.getGameSessionById(anyString()))
        .willReturn(Optional.ofNullable(null));

    // when, then
    assertThrows(
        InvalidGameSessionException.class,
        () -> gameSessionService.makeTry(exampleGameId, exampleUserGuess));
  }
}
