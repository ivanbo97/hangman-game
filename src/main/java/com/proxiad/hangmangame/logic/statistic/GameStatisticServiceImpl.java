package com.proxiad.hangmangame.logic.statistic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.GameSession;
import com.proxiad.hangmangame.model.GameStatistic;

@Service
public class GameStatisticServiceImpl implements GameStatisticService {

  @Autowired GameStatisticRepository gameStatRepo;

  @Override
  public Optional<GameStatistic> findStatisticByGameSession(GameSession gameSession) {

    return gameStatRepo.findGameStatisticByGameSession(gameSession);
  }
}
