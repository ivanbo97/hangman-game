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
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.repository.WordRepository;

@ExtendWith(MockitoExtension.class)
class GameSessionServiceImplTest {

  @Mock
  private WordRepository wordRepository;

  @InjectMocks
  private GameSessionServiceImpl gameSessionService;

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void makeTryTest(String wordToGuess, char userInputLetter, int lettersToGuessLeft) {

    // given
    GameSession gameSession = new GameSession(wordToGuess);
    gameSession.setTriesLeft(5);
    // when
    gameSessionService.makeTry(gameSession, userInputLetter);

    int receivedLettersToGuess = gameSession.getLettersToGuessLeft();

    // then
    assertThat(receivedLettersToGuess).isEqualTo(lettersToGuessLeft);
    assertThat(gameSession.getTriesLeft()).isEqualTo(5 - 1);

  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("stack", 'j', 5), Arguments.of("storm", 's', 4),
        Arguments.of("interface", 'e', 7), Arguments.of("alaska", 'a', 3));
  }

  @Test
  void repetitiveLetterEnteringTest() throws Exception {

    // given
    GameSession gameSession = new GameSession("interface");
    gameSession.setPuzzledWord("___e____e");
    gameSession.setLettersToGuessLeft(7);
    char userInput = 'e';

    // when
    gameSessionService.makeTry(gameSession, userInput);
    int lettersToGuessLeft = gameSession.getLettersToGuessLeft();
    // then
    assertThat(lettersToGuessLeft).isEqualTo(7);

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
