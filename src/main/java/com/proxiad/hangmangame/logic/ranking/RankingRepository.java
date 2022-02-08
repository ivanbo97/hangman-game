package com.proxiad.hangmangame.logic.ranking;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.model.GameRanking;

@Repository
public interface RankingRepository extends JpaRepository<GameRanking, String> {

  Optional<GameRanking> getRankingByGamerName(String gamerId);

  List<GameRanking> findTop10ByOrderByTotalWinsDescGamerNameAsc();
}
