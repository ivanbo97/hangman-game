package com.proxiad.task.ivanboyukliev.hangmangame.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_BASE_URL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_LETTER_MSG;
import javax.servlet.ServletException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;

@Controller
@RequestMapping(GAME_BASE_URL)
@Validated
public class GamePlayController {

  @Autowired private GameSessionService gameSessionService;

  @GetMapping("/{gameId}")
  public String initiateGame(@PathVariable String gameId, Model model) throws ServletException {

    GameSession currentGameSession = gameSessionService.getGameSessionById(gameId);
    model.addAttribute("gameSessionObj", currentGameSession);
    return "hangmanMainPage";
  }

  @PostMapping("/{gameId}")
  public String makeGuess(
      @RequestParam @Pattern(regexp = "[a-z]{1}", message = INVALID_LETTER_MSG)
          String enteredLetter,
      @PathVariable @NotBlank String gameId)
      throws ServletException {

    GameSession gameSession = gameSessionService.makeTry(gameId, enteredLetter);

    if (gameSession.getLettersToGuessLeft() == 0 || gameSession.getTriesLeft() == 0) {
      return "redirect:" + GAME_BASE_URL + "/" + gameId + "/result";
    }

    return "redirect:" + GAME_BASE_URL + "/" + gameId;
  }
}
