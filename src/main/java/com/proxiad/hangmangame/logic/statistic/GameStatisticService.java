package com.proxiad.hangmangame.logic.statistic;

import java.util.Optional;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.statistic.GameStatistic;

public interface GameStatisticService {

  Optional<GameStatistic> findStatisticByGameSession(GameSession gameSession);
}
