package it.com.proxiad.hangmangame.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.web.GamePlayController;
import static com.proxiad.hangmangame.logic.game.GameConstants.BONUS_TRIES;
import static com.proxiad.hangmangame.web.ControllerConstants.GAME_BASE_URL;

@ExtendWith(MockitoExtension.class)
class GamePlayControllerIT {

  @Mock private GameSessionService gameSessionService;

  @InjectMocks private GamePlayController gamePlayController;

  private MockMvc mockMvc;

  private GameSession gameSession;

  private String exampleId = "1-A-B3234ASCZG ";

  @BeforeEach
  void setUp() {

    this.mockMvc = MockMvcBuilders.standaloneSetup(gamePlayController).build();
    gameSession = new GameSession();
    gameSession.setGameId(exampleId);
  }

  @Test
  void initiateGameTest() throws Exception {

    // given
    gameSession.setWordToGuess("exampleword");
    given(gameSessionService.getGameSessionById(anyString())).willReturn(gameSession);

    // when, then
    mockMvc
        .perform(get(GAME_BASE_URL + "/{gameId}", exampleId))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("gameSessionObj"))
        .andExpect(view().name("hangmanMainPage"));
  }

  @Test
  void resultPageRedirectTest() throws Exception {
    // given
    gameSession.setWordToGuess("interface");
    gameSession.setPuzzledWord("interface");
    gameSession.setTriesLeft(0);
    gameSession.setLettersToGuessLeft(0);

    given(gameSessionService.makeTry(anyString(), any())).willReturn(gameSession);

    // when, then
    mockMvc
        .perform(post(GAME_BASE_URL + "/{gameId}", exampleId).param("enteredLetter", "a"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:" + GAME_BASE_URL + "/" + exampleId + "/result"));
  }

  @Test
  void gameplayPageRedirectTest() throws Exception {
    // given
    gameSession.setWordToGuess("interface");
    gameSession.setPuzzledWord("i__e__a_e");
    gameSession.setTriesLeft(6 + BONUS_TRIES);
    gameSession.setLettersToGuessLeft(6);

    // when, then
    given(gameSessionService.makeTry(anyString(), any())).willReturn(gameSession);
    mockMvc
        .perform(post(GAME_BASE_URL + "/{gameId}", exampleId).param("enteredLetter", "a"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:" + GAME_BASE_URL + "/" + exampleId));
  }
}
