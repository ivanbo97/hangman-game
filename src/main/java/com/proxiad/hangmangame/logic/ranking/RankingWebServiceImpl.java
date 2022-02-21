package com.proxiad.hangmangame.logic.ranking;

import static com.proxiad.hangmangame.model.statistic.GameStatisticSpecifications.haveWonGamesForLastNDays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.ranking.RankingModel;
import com.proxiad.hangmangame.model.ranking.RankingRepository;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import com.proxiad.hangmangame.model.statistic.GameStatisticRepository;

@WebService(endpointInterface = "com.proxiad.hangmangame.logic.ranking.RankingWebService")
@Service
public class RankingWebServiceImpl implements RankingWebService {

  @Autowired private RankingRepository rankingRepository;

  @Autowired private GameStatisticRepository gameStatRepo;

  @Override
  public List<RankingModel> getTop10Players() {
    return rankingRepository.findTop10ByOrderByTotalWinsDescGamerNameAsc().stream()
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }

  @Override
  public List<RankingModel> getTop10ForLastMonth() {
    int DAYS_IN_MONTH = 30;
    List<GameStatistic> gameStats = gameStatRepo.findAll(haveWonGamesForLastNDays(DAYS_IN_MONTH));
    Map<String, Integer> playersAndWinsMap =
        GameStatsDataExtractor.getTotalWinsForEachPlayer(gameStats);

    return playersAndWinsMap.entrySet().stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(10)
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }
}
