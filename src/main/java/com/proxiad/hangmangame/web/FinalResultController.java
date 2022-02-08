package com.proxiad.hangmangame.web;

import static com.proxiad.hangmangame.web.ControllerConstants.FAILURE_MSG;
import static com.proxiad.hangmangame.web.ControllerConstants.FAILURE_PAGE_TITLE;
import static com.proxiad.hangmangame.web.ControllerConstants.GAME_BASE_URL;
import static com.proxiad.hangmangame.web.ControllerConstants.SUCCESS_MSG;
import static com.proxiad.hangmangame.web.ControllerConstants.SUCCESS_PAGE_TITLE;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.model.GameSession;

@Controller
@RequestMapping(GAME_BASE_URL)
@Validated
public class FinalResultController {

  @Autowired private GameSessionService gameSessionService;

  @Autowired RankingService rankingService;

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

    String inputFieldForPlayerName =
        "<form method=\"post\" action=\"/hangman-game/stats\""
            + " class=\"inline\">"
            + "<label for=\"gamerName\">You can enter name for keeping statistics:</label><br>"
            + "<input type=\"text\" id=\"gamerName\" name=\"gamerName\">"
            + "<input type=\"hidden\" id=\"gameId\" name=\"gameId\" value="
            + gameId
            + "><br>"
            + "<button type=\"submit\" class=\"link-button\">Send Name</button></form>";
    model.addAttribute("inputFieldForPlayerName", inputFieldForPlayerName);
    model.addAttribute("topPlayers", rankingService.getTop10Players());
    return "rankingPage";
  }
}
