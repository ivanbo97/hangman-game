package com.proxiad.hangmangame.logic;

import static com.proxiad.hangmangame.logic.GameConstants.BONUS_TRIES;
import static com.proxiad.hangmangame.logic.GameConstants.SECRET_ENCODE_VAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GameSessionTest {

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void generatePuzzledWordTest(String wordToGuess, String expectedPuzzledWord) {

    // given
    GameSession gameSession = new GameSession(wordToGuess);

    // when, then
    assertThat(gameSession.getPuzzledWord()).isNotNull();
    assertThat(gameSession.getPuzzledWord()).isEqualTo(expectedPuzzledWord);
    int triesLeft = gameSession.getTriesLeft();
    int numOfGuessLetters = wordToGuess.length() - gameSession.getLettersToGuessLeft();
    assertThat(triesLeft).isEqualTo(wordToGuess.length() - numOfGuessLetters + BONUS_TRIES);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(
        Arguments.of("stack", "s___k"),
        Arguments.of("kilowatt", "k_____tt"),
        Arguments.of("interface", "i__e____e"));
  }

  @Test
  void encodeLettersToBeGuessedTest() {

    // given
    GameSession gameSession = new GameSession("interface");
    String encodedLettersToGuess = gameSession.getLettersToBeGuessed();

    // when,then
    for (char encodeLetter : encodedLettersToGuess.toCharArray()) {
      char decodedLetter = (char) (encodeLetter + SECRET_ENCODE_VAL);
      assertThat(gameSession.getWordToGuess().contains(String.valueOf(decodedLetter))).isTrue();
    }
  }
}
