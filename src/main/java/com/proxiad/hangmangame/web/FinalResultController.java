package com.proxiad.hangmangame.web;

import static com.proxiad.hangmangame.util.ApplicationConstants.FAILURE_MSG;
import static com.proxiad.hangmangame.util.ApplicationConstants.FAILURE_PAGE_TITLE;
import static com.proxiad.hangmangame.util.ApplicationConstants.GAME_BASE_URL;
import static com.proxiad.hangmangame.util.ApplicationConstants.SUCCESS_MSG;
import static com.proxiad.hangmangame.util.ApplicationConstants.SUCCESS_PAGE_TITLE;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proxiad.hangmangame.logic.GameSession;
import com.proxiad.hangmangame.logic.GameSessionService;
import com.proxiad.hangmangame.logic.InvalidGameSessionException;

@Controller
@RequestMapping(GAME_BASE_URL)
@Validated
public class FinalResultController {

  @Autowired private GameSessionService gameSessionService;

  @GetMapping("/{gameId}/result")
  public String showFinalResult(@PathVariable @NotBlank String gameId, Model model)
      throws InvalidGameSessionException {

    GameSession gameSession = gameSessionService.getGameSessionById(gameId);

    if (gameSession.getLettersToGuessLeft() == 0) {
      model.addAttribute("pageTitle", SUCCESS_PAGE_TITLE);
      model.addAttribute("gameResult", String.format(SUCCESS_MSG, gameSession.getWordToGuess()));
    } else if (gameSession.getTriesLeft() > 0) {
      return "redirect:" + GAME_BASE_URL + "/" + gameId;
    } else {
      // No more tries left
      model.addAttribute("pageTitle", FAILURE_PAGE_TITLE);
      model.addAttribute("gameResult", String.format(FAILURE_MSG, gameSession.getWordToGuess()));
    }
    gameSessionService.deleteSessionById(gameId);
    return "finalResultPage";
  }
}
