package it.tests;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.APP_BASE_URL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.HOST_ADDR;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.PORT;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.PROTOCOL;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import it.WebDriverSetupTest;
import it.pages.IndexPage;

public class IndexPageIT extends WebDriverSetupTest {

  private IndexPage indexPage;

  @BeforeEach
  public void initIndexPage() {
    indexPage = PageFactory.initElements(webDriver, IndexPage.class);
  }

  @AfterEach
  public void tearDown() {
    webDriver.close();
  }

  @Test
  public void pageTitleTest() {
    String pageTitle = webDriver.getTitle();
    assertThat(pageTitle).isEqualTo("Hangman Game");
  }

  @Test
  public void urlOnGameStartTest() {
    indexPage.initiateGame();
    String urlAfterGameStart = webDriver.getCurrentUrl();
    assertThat(urlAfterGameStart)
        .matches(
            PROTOCOL
                + "://"
                + HOST_ADDR
                + ":"
                + PORT
                + APP_BASE_URL
                + GAME_BASE_URL
                + "/"
                + "\\S+");
  }
}
