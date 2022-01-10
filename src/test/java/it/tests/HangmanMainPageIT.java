package it.tests;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.BONUS_TRIES;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.FAILURE_PAGE_TITLE;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.SECRET_ENCODE_VAL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.SUCCESS_PAGE_TITLE;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.UNKNOWN_LETTER_SYMBOL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.PageFactory;
import it.WebDriverSetupTest;
import it.pages.HangmanMainPage;

public class HangmanMainPageIT extends WebDriverSetupTest {

  private HangmanMainPage gamePage;

  @BeforeEach
  public void innitGamePage() {
    gamePage = PageFactory.initElements(webDriver, HangmanMainPage.class);
    gamePage.initiateGame();
  }

  @AfterEach
  public void tearDown() {
    webDriver.close();
  }

  @Test
  public void firstAndLastLetterAreVisibleTest() {

    String puzzledWord = gamePage.getPuzzledWord();
    char firstLetter = puzzledWord.charAt(0);
    char lastLetter = puzzledWord.charAt(puzzledWord.length() - 1);
    char hiddenLetterSymbol = UNKNOWN_LETTER_SYMBOL.charAt(0);

    assertThat(firstLetter).isNotEqualTo(hiddenLetterSymbol);
    assertThat(lastLetter).isNotEqualTo(hiddenLetterSymbol);
  }

  @Test
  public void triesDecreaseTest() {
    int initialTries = Integer.valueOf(gamePage.getTriesLeft());
    gamePage.enterLetter("a");
    gamePage.makeGuess();
    int triesAfterSingleGuess = Integer.valueOf(gamePage.getTriesLeft());
    assertThat(triesAfterSingleGuess).isEqualTo(initialTries - 1);
  }

  @Test
  public void lettersToGuessTest() {
    int initialLettersToGuess = Integer.valueOf(gamePage.getNumOfLettersToGuess());
    char hiddenLetterSymbol = UNKNOWN_LETTER_SYMBOL.charAt(0);

    String puzzledWord = gamePage.getPuzzledWord();
    long numOfHiddenLetters =
        puzzledWord.chars().filter(letter -> letter == hiddenLetterSymbol).count();

    assertThat(initialLettersToGuess).isEqualTo(numOfHiddenLetters);
  }

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  public void invalidInputTest(String invalidInput) {
    int intialTries = Integer.valueOf(gamePage.getTriesLeft());
    gamePage.enterLetter(invalidInput);
    gamePage.makeGuess();

    int triesAfterLetterInput = Integer.valueOf(gamePage.getTriesLeft());

    // Verify that request didn't reached server
    assertThat(triesAfterLetterInput).isEqualTo(intialTries);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("aaa"), Arguments.of("?"), Arguments.of("1"),

        /* Arguments.of(" "), Arguments.of(""), */ Arguments.of("a1b2/"));
  }

  @Test
  public void enteringAlreadyKnownLetterTest() {

    String puzzledWord = gamePage.getPuzzledWord();
    int intialTries = Integer.valueOf(gamePage.getTriesLeft());
    int intialLettersToGuess = Integer.valueOf(gamePage.getNumOfLettersToGuess());
    String firstLetter = String.valueOf(puzzledWord.charAt(0));

    // Enter letter that is already visible on screen
    gamePage.enterLetter(firstLetter);
    gamePage.makeGuess();

    int triesAfterGuess = Integer.valueOf(gamePage.getTriesLeft());
    int lettersAfterGuess = Integer.valueOf(gamePage.getNumOfLettersToGuess());

    assertThat(triesAfterGuess).isEqualTo(intialTries - 1);
    assertThat(lettersAfterGuess).isEqualTo(intialLettersToGuess);
  }

  @Test
  public void redirectToSuccessPageTest() {

    String lettersToGuessEncoded = gamePage.getLettersToGuessEncoded();
    String lettersToGuessDecoded =
        lettersToGuessEncoded.chars().map(character -> character + SECRET_ENCODE_VAL)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();


    for (char guessLetter : lettersToGuessDecoded.toCharArray()) {
      gamePage.enterLetter(String.valueOf(guessLetter));
      gamePage.makeGuess();
    }

    assertThat(webDriver.getCurrentUrl()).endsWith("/result");
    assertThat(webDriver.getTitle()).isEqualTo(SUCCESS_PAGE_TITLE);
  }

  @Test
  public void redirectToFailPageTest() {
    int numOfLettersToGuess = Integer.valueOf(gamePage.getNumOfLettersToGuess());

    for (int i = 0; i < numOfLettersToGuess + BONUS_TRIES; i++) {
      gamePage.enterLetter("z");
      gamePage.makeGuess();
    }

    assertThat(webDriver.getCurrentUrl()).endsWith("/result");
    assertThat(webDriver.getTitle()).isEqualTo(FAILURE_PAGE_TITLE);
  }
}
