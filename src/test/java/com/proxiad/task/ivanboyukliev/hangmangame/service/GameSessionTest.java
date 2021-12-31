package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.BONUS_TRIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GameSessionTest {

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void generatePuzzledWordTest(String wordToGuess, String expectedPuzzledWord) {

    // given
    GameSession gameSession = new GameSession(wordToGuess);

    // assert
    assertThat(gameSession.getPuzzledWord()).isNotNull();
    assertThat(gameSession.getPuzzledWord()).isEqualTo(expectedPuzzledWord);
    int triesLeft = gameSession.getTriesLeft();
    int numOfGuessLetters = wordToGuess.length() - gameSession.getLettersToGuessLeft();
    assertThat(triesLeft).isEqualTo(wordToGuess.length() - numOfGuessLetters + BONUS_TRIES);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("stack", "s___k"), Arguments.of("kilowatt", "k_____tt"),
        Arguments.of("interface", "i__e____e"));
  }
}
