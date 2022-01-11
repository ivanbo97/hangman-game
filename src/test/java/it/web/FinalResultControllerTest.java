package it.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.BONUS_TRIES;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.proxiad.task.ivanboyukliev.hangmangame.WebConfiguration;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import com.proxiad.task.ivanboyukliev.hangmangame.web.FinalResultController;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
class FinalResultControllerTest {

  @Mock private GameSessionService gameSessionService;

  @InjectMocks private FinalResultController finalResultController;

  private MockMvc mockMvc;

  private GameSession gameSession;

  private String exampleId = "1-A-B3234ASCZG";

  @BeforeEach
  void setUp() {
    gameSession = new GameSession();
    gameSession.setGameId(exampleId);
    this.mockMvc = MockMvcBuilders.standaloneSetup(finalResultController).build();
  }

  @Test
  void successPageRedirectTest() throws Exception {

    // given
    gameSession.setWordToGuess("interface");
    gameSession.setPuzzledWord("interface");
    gameSession.setTriesLeft(0);
    gameSession.setLettersToGuessLeft(0);

    given(gameSessionService.getGameSessionById(anyString())).willReturn(gameSession);

    // when, then
    mockMvc
        .perform(get(GAME_BASE_URL + "/{gameId}/result", exampleId))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("pageTitle"))
        .andExpect(model().attributeExists("gameResult"))
        .andExpect(view().name("finalResultPage"));
  }

  @Test
  void gamePlayPageRedirect() throws Exception {

    // given
    gameSession.setWordToGuess("interface");
    gameSession.setPuzzledWord("i__e____e");
    gameSession.setTriesLeft(6 + BONUS_TRIES);
    gameSession.setLettersToGuessLeft(6);

    given(gameSessionService.getGameSessionById(anyString())).willReturn(gameSession);

    // when, then
    mockMvc
        .perform(get(GAME_BASE_URL + "/{gameId}/result", exampleId))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:" + GAME_BASE_URL + "/" + exampleId));
  }
}
