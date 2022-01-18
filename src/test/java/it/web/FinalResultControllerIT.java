package it.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.proxiad.hangmangame.logic.GameSession;
import com.proxiad.hangmangame.logic.GameSessionService;
import com.proxiad.hangmangame.web.FinalResultController;
import static com.proxiad.hangmangame.util.ApplicationConstants.BONUS_TRIES;
import static com.proxiad.hangmangame.util.ApplicationConstants.GAME_BASE_URL;

@ExtendWith(MockitoExtension.class)
class FinalResultControllerIT {

  @Mock private GameSessionService gameSessionService;

  @InjectMocks private FinalResultController finalResultController;

  private MockMvc mockMvc;

  private GameSession gameSession;

  private String exampleId = "1-A-B3234ASCZG";

  @BeforeEach
  void setUp() {

    mockMvc = MockMvcBuilders.standaloneSetup(finalResultController).build();
    gameSession = new GameSession();
    gameSession.setGameId(exampleId);
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
