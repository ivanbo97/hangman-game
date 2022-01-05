package com.proxiad.task.ivanboyukliev.hangmangame.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;


@Controller
@RequestMapping(GAME_BASE_URL)
public class StartNewGameController {

  @Autowired
  private GameSessionService gameSessionService;

  @PostMapping
  public String startGame(Model model) {

    GameSession newSession = gameSessionService.startNewGame();
    model.addAttribute("gameSessionObj", newSession);
    return "redirect:" + GAME_BASE_URL + "/" + newSession.getGameId();
  }

}
