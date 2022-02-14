package com.proxiad.hangmangame.model.statistic;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.model.game.GameSession;

@Repository
public interface GameStatisticRepository
    extends JpaRepository<GameStatistic, Long>, JpaSpecificationExecutor<GameStatistic> {

  Optional<GameStatistic> findGameStatisticByGameSession(GameSession gameSession);
}
