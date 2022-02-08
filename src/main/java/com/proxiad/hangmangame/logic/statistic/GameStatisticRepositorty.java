package com.proxiad.hangmangame.logic.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.model.GameStatistic;

@Repository
public interface GameStatisticRepositorty
    extends JpaRepository<GameStatistic, Long>, JpaSpecificationExecutor<GameStatistic> {}
