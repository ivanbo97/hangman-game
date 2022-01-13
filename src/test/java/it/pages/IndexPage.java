package it.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage {

  @FindBy(xpath = "//*[@id=\"startGameBtn\"]")
  private WebElement startGameBtn;

  public void initiateGame() {
    startGameBtn.click();
  }
}
