package com.proxiad.hangmangame.web;

import static com.proxiad.hangmangame.web.ControllerConstants.GAME_BASE_URL;
import static com.proxiad.hangmangame.web.ControllerConstants.INVALID_LETTER_MSG;
import javax.servlet.ServletException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.model.game.GameMakeTryRequest;
import com.proxiad.hangmangame.model.game.GameSession;

@Controller
@RequestMapping(GAME_BASE_URL)
@Validated
public class GamePlayController {

  @Autowired private GameSessionService gameSessionService;

  @Autowired private RankingService rankingService;

  @GetMapping("/{gameId}")
  public String initiateGame(@PathVariable String gameId, Model model) throws ServletException {

    GameSession currentGameSession = gameSessionService.getGameSessionById(gameId);
    model.addAttribute("gameSessionObj", currentGameSession);
    if (currentGameSession.getLettersToGuessLeft() == 0 || currentGameSession.getTriesLeft() == 0) {
      model.addAttribute("topPlayers", rankingService.getTop10Players());
      return "rankingPage";
    }
    return "hangmanMainPage";
  }

  @PostMapping("/{gameId}")
  public String makeGuess(
      @RequestParam @Pattern(regexp = "[a-z]{1}", message = INVALID_LETTER_MSG)
          String enteredLetter,
      @PathVariable @NotBlank String gameId) {

    GameMakeTryRequest gameTry = new GameMakeTryRequest();
    gameTry.setGuessLetter(enteredLetter);
    GameSession gameSession = gameSessionService.makeTry(gameId, gameTry);
    if (gameSession.getLettersToGuessLeft() == 0 || gameSession.getTriesLeft() == 0) {
      return "redirect:" + GAME_BASE_URL + "/" + gameId + "/result";
    }
    return "redirect:" + GAME_BASE_URL + "/" + gameId;
  }
}
