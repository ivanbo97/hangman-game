package it.web;

import static com.proxiad.hangmangame.util.ApplicationConstants.GAME_BASE_URL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.proxiad.hangmangame.WebConfiguration;
import com.proxiad.hangmangame.logic.GameSession;
import com.proxiad.hangmangame.logic.GameSessionService;
import com.proxiad.hangmangame.web.StartNewGameController;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
public class StartNewGameControllerTest {

  @Mock private GameSessionService gameSessionService;

  @InjectMocks private StartNewGameController startNewGameController;

  private GameSession gameSession;

  private MockMvc mockMvc;

  private String exampleId = "1-A-B3234ASCZG";

  @BeforeEach
  void startUp() {
    gameSession = new GameSession("exampleWord");
    gameSession.setGameId(exampleId);
    this.mockMvc = MockMvcBuilders.standaloneSetup(startNewGameController).build();
  }

  @Test
  void startGameTest() throws Exception {

    // given
    given(gameSessionService.startNewGame()).willReturn(gameSession);

    // when,then
    mockMvc
        .perform(post("/games"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeExists("gameSessionObj"))
        .andExpect(view().name("redirect:" + GAME_BASE_URL + "/" + exampleId));
  }
}
