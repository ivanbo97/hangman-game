package it;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class WebDriverSetupTest {

  protected WebDriver webDriver;

  @Container public static GenericContainer hangmanGameContainer;

  static {
    hangmanGameContainer =
        new GenericContainer(DockerImageName.parse("hman-test:latest"))
            .withEnv("SPRING_PROFILES_ACTIVE", "test")
            .withExposedPorts(8080);
  }

  @BeforeEach
  public void setup() {

    System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
    webDriver = new ChromeDriver();
    webDriver.manage().window().maximize();
    webDriver.manage().deleteAllCookies();
    webDriver.get(
        "http://localhost:" + hangmanGameContainer.getMappedPort(8080) + "/hangman-game/");
  }
}
