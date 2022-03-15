package com.proxiad.hangmangame.api.statistic;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proxiad.hangmangame.api.BadRequestException;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.logic.statistic.GameStatisticService;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.statistic.GameStatRequest;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class GameStatisticApi {

  private final GameStatisticService gameStatService;
  private final RankingService rankingService;
  private final GameSessionService gameSessionService;

  @PostMapping()
  @Operation(summary = "Attach statistic with gamer name to specific game")
  public ResponseEntity<Void> generateStatisticForGame(
      @Valid @RequestBody GameStatRequest statRequest) {

    String gameId = statRequest.getGameId();
    GameSession retrievedSession = gameSessionService.getGameSessionById(gameId);
    Optional<GameStatistic> retirevedStatistic =
        gameStatService.findStatisticByGameSession(retrievedSession);

    if (retirevedStatistic.isPresent()) {
      throw new BadRequestException("Game with ID [%s] already has statistic".formatted(gameId));
    }
    rankingService.createRankingForPlayer(gameId, statRequest.getGamerName());
    return ResponseEntity.ok().build();
  }
}
