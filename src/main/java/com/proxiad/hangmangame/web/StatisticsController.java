package com.proxiad.hangmangame.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.logic.statistic.GameStatisticService;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.statistic.GameStatistic;

@Controller
@RequestMapping("/stats")
public class StatisticsController {

  @Autowired private RankingService gameRankingService;

  @Autowired private GameSessionService gameSessionService;

  @Autowired private GameStatisticService gameStatsService;

  @PostMapping
  public String generateStatitics(
      @RequestParam("gameId") String gameId, @RequestParam("gamerName") String gamerName)
      throws InvalidGameSessionException {

    GameSession retrievedSession = gameSessionService.getGameSessionById(gameId);
    Optional<GameStatistic> retirevedStatistic =
        gameStatsService.findStatisticByGameSession(retrievedSession);

    if (retirevedStatistic.isPresent()) {
      /*User is trying to cheat - accumulating wins
       *  based on already finished games!
       */
      return "redirect:/";
    }

    gameRankingService.createRankingForPlayer(gameId, gamerName);
    return "redirect:/";
  }
}
