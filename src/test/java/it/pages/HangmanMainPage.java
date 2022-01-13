package it.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HangmanMainPage {

  @FindBy(css = "p#puzzled-word")
  private WebElement puzzledWord;

  @FindBy(css = "p#tries-left")
  private WebElement triesLeft;

  @FindBy(css = "p#letters-to-guess")
  private WebElement numOfLettersToGuess;

  @FindBy(css = "input#enteredLetter")
  private WebElement inputLetterField;

  @FindBy(css = "input#submit-letter-btn")
  private WebElement submitLetterBtn;

  @FindBy(css = "button#startGameBtn")
  private WebElement hiddenStartGameBtn;

  @FindBy(css = "p#secret-letters")
  private WebElement lettersToGuess;

  public void initiateGame() {
    hiddenStartGameBtn.click();
  }

  public String getPuzzledWord() {
    return puzzledWord.getText();
  }

  public void enterLetter(String letter) {
    inputLetterField.sendKeys(letter);
  }

  public void makeGuess() {
    submitLetterBtn.click();
  }

  public String getTriesLeft() {
    return triesLeft.getText();
  }

  public String getNumOfLettersToGuess() {
    return numOfLettersToGuess.getText();
  }

  public String getLettersToGuessEncoded() {
    return lettersToGuess.getText();
  }
}
