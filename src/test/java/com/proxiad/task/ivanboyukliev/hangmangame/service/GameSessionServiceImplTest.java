package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.repository.WordRepository;
import com.proxiad.task.ivanboyukliev.hangmangame.validator.UserInputValidator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceImplTest {

  @Mock
  private WordRepository wordRepository;

  @Mock
  private UserInputValidator inputValidator;

  @Mock
  private ServletContext servletContext;

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
    given(servletContext.getAttribute(anyString())).willReturn(gameSession);

    // when
    gameSessionService.makeTry(servletContext, exampleUri, userInputLetter);

    int receivedLettersToGuess = gameSession.getLettersToGuessLeft();

    // then
    assertThat(receivedLettersToGuess).isEqualTo(lettersToGuessLeft);
    assertThat(gameSession.getTriesLeft()).isEqualTo(initialTries - 1);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("stack", "j", 5), Arguments.of("storm", "s", 4),
        Arguments.of("interface", "e", 7), Arguments.of("alaska", "a", 3));
  }

  @Test
  void repetitiveLetterEnteringTest() throws Exception {

    // given
    GameSession gameSession = new GameSession("interface");
    gameSession.setPuzzledWord("___e____e");
    gameSession.setLettersToGuessLeft("___e____e".length() - 2);
    String userInput = "e";
    int initialTriesLeft = gameSession.getTriesLeft();
    given(servletContext.getAttribute(anyString())).willReturn(gameSession);

    // when
    gameSessionService.makeTry(servletContext, exampleUri, userInput);
    int lettersToGuessLeft = gameSession.getLettersToGuessLeft();
    // then
    assertThat(lettersToGuessLeft).isEqualTo(7);
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
  void reloadGameTest() throws InvalidGameSessionException {

    // given
    GameSession gameSession = new GameSession("");
    given(servletContext.getAttribute(anyString())).willReturn(gameSession);

    // when
    GameSession newSession = gameSessionService.reloadGame(servletContext, exampleUri);

    // then
    then(inputValidator).should().validateGameSessionId(any(ServletContext.class), eq("A12BD13D"));
    assertThat(newSession).isNotNull();
  }
}
