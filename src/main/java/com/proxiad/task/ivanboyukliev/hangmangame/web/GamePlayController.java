package com.proxiad.task.ivanboyukliev.hangmangame.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;

@Controller
@RequestMapping(GAME_BASE_URL)
public class GamePlayController {

  @Autowired
  private GameSessionService gameSessionService;


  @GetMapping("/{gameId}")
  public String initiateGame(@PathVariable String gameId, Model model) throws ServletException {

    GameSession currentGameSession = gameSessionService.getGameSessionById(gameId);
    gameSessionService.validateGameExistance(currentGameSession);

    model.addAttribute("gameSessionObj", currentGameSession);

    return "hangmanMainPage";
  }

  @PostMapping("/{gameId}")
  public String makeGuess(@RequestParam String enteredLetter, @PathVariable String gameId,
      Model model) throws ServletException {

    GameSession gameSession = gameSessionService.makeTry(gameId, enteredLetter);

    if (gameSession.getLettersToGuessLeft() == 0 || gameSession.getTriesLeft() == 0) {
      return "redirect:" + GAME_BASE_URL + "/" + gameId + "/result";
    }

    return "redirect:" + GAME_BASE_URL + "/" + gameId;
  }

}
