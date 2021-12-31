package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceImplTest {

  @Mock
  private WordRepository wordRepository;

  @Mock
  private UserInputValidator inputValidator;

  @InjectMocks
  private GameSessionServiceImpl gameSessionService;

  private String exampleUri = "hangman-game/games/A12BD13D";

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void makeTryTest(String wordToGuess, String userInputLetter, int lettersToGuessLeft)
      throws ServletException {

    // given
    GameSession gameSession = new GameSession(wordToGuess);
    int initialTries = gameSession.getTriesLeft();

    // when
    gameSessionService.makeTry(gameSession, userInputLetter);

    int receivedLettersToGuess = gameSession.getLettersToGuessLeft();

    // then
    assertThat(receivedLettersToGuess).isEqualTo(lettersToGuessLeft);
    assertThat(gameSession.getTriesLeft()).isEqualTo(initialTries - 1);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("stack", "j", 3), Arguments.of("storm", "o", 2),
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

    // when
    GameSession newSession = gameSessionService.makeTry(gameSession, userInput);
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
}
