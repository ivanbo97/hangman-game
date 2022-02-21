package com.proxiad.hangmangame.logic.statistic;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import com.proxiad.hangmangame.model.statistic.GameStatisticRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameStatisticServiceImpl implements GameStatisticService {

  private final GameStatisticRepository gameStatRepo;

  @Override
  public Optional<GameStatistic> findStatisticByGameSession(GameSession gameSession) {

    return gameStatRepo.findGameStatisticByGameSession(gameSession);
  }
}
