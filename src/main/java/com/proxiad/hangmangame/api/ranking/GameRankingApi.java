package com.proxiad.hangmangame.api.ranking;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.model.ranking.RankingModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class GameRankingApi {

  @Autowired RankingService rankingService;

  @GetMapping(
      value = "/top10",
      produces = {"application/json"})
  @Operation(summary = "Get top 10 players")
  public ResponseEntity<List<RankingModel>> getTop10PlayersEver() {
    return ResponseEntity.ok(rankingService.getTop10Players());
  }

  @GetMapping(
      value = "/top10-last-month",
      produces = {"application/json"})
  @Operation(summary = "Get top 10 players for last month")
  public ResponseEntity<List<RankingModel>> getTop10PlayersForLastMonth() {
    return ResponseEntity.ok(rankingService.getTopNPlayersForLastNDays(10, 30));
  }
}
