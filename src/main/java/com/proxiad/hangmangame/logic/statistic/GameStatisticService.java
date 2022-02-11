package com.proxiad.hangmangame.logic.statistic;

import java.util.Optional;
import com.proxiad.hangmangame.model.GameSession;
import com.proxiad.hangmangame.model.GameStatistic;

public interface GameStatisticService {

  Optional<GameStatistic> findStatisticByGameSession(GameSession gameSession);
}
