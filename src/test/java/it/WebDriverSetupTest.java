package it;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSetupTest {

  protected WebDriver webDriver;

  @BeforeEach
  public void setup() {

    System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
    webDriver = new ChromeDriver();
    webDriver.manage().window().maximize();
    webDriver.manage().deleteAllCookies();
    webDriver.get("http://localhost:8080/hangman-game/");
  }
}
