package it.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.model.GameSession;
import com.proxiad.hangmangame.web.StartNewGameController;
import static com.proxiad.hangmangame.web.ControllerConstants.GAME_BASE_URL;

@ExtendWith(MockitoExtension.class)
class StartNewGameControllerIT {

  @Mock private GameSessionService gameSessionService;

  @InjectMocks private StartNewGameController startNewGameController;

  private GameSession gameSession;

  private MockMvc mockMvc;

  private String exampleId = "1-A-B3234ASCZG";

  @BeforeEach
  void startUp() {

    this.mockMvc = MockMvcBuilders.standaloneSetup(startNewGameController).build();
    gameSession = new GameSession("exampleWord");
    gameSession.setGameId(exampleId);
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
