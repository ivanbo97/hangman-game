package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.FAILURE_MSG;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.SUCCESS_MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(GAME_BASE_URL)
public class FinalResultController {

  @Autowired
  private GameSessionService gameSessionService;

  @Autowired
  private UserInputValidator inputValidator;

  @GetMapping("/{gameId}/result")
  public String showFinalResult(@PathVariable String gameId, Model model)
      throws InvalidGameSessionException {

    GameSession gameSession = gameSessionService.getGameSessionById(gameId);
    inputValidator.validateGameSessionExistance(gameSession);

    if (gameSession.getLettersToGuessLeft() == 0) {
      model.addAttribute("gameResult", String.format(SUCCESS_MSG, gameSession.getWordToGuess()));
    } else {
      // No more tries left
      model.addAttribute("gameResult", String.format(FAILURE_MSG, gameSession.getWordToGuess()));
    }
    gameSessionService.deleteSessionById(gameId);
    return "finalResultPage";
  }

}
