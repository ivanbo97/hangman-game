package com.proxiad.hangmangame.model.ranking;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<GameRanking, String> {

  Optional<GameRanking> getRankingByGamerName(String gamerId);

  List<GameRanking> findTop10ByOrderByTotalWinsDescGamerNameAsc();
}
