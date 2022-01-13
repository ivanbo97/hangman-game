package com.proxiad.hangmangame.web;

import static com.proxiad.hangmangame.util.ApplicationConstants.GAME_BASE_URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proxiad.hangmangame.logic.GameSession;
import com.proxiad.hangmangame.logic.GameSessionService;

@Controller
@RequestMapping(GAME_BASE_URL)
public class StartNewGameController {

  @Autowired private GameSessionService gameSessionService;

  @PostMapping
  public String startGame(Model model) {

    GameSession newSession = gameSessionService.startNewGame();
    model.addAttribute("gameSessionObj", newSession);
    return "redirect:" + GAME_BASE_URL + "/" + newSession.getGameId();
  }
}
