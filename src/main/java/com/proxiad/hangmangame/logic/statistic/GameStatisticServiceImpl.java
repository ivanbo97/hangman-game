package com.proxiad.hangmangame.logic.statistic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import com.proxiad.hangmangame.model.statistic.GameStatisticRepository;

@Service
public class GameStatisticServiceImpl implements GameStatisticService {

  @Autowired GameStatisticRepository gameStatRepo;

  @Override
  public Optional<GameStatistic> findStatisticByGameSession(GameSession gameSession) {

    return gameStatRepo.findGameStatisticByGameSession(gameSession);
  }
}
