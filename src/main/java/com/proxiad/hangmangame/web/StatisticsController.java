package com.proxiad.hangmangame.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.logic.ranking.RankingService;

@Controller
@RequestMapping("/stats")
public class StatisticsController {

  @Autowired private RankingService gameRankingService;

  @PostMapping
  public String generateStatitics(
      @RequestParam("gameId") String gameId, @RequestParam("gamerName") String gamerName)
      throws InvalidGameSessionException {
    gameRankingService.createRankingForPlayer(gameId, gamerName);
    return "redirect:/";
  }
}
