package com.proxiad.hangmangame.logic.ranking;

import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.logic.game.GameSessionServiceImpl;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.game.GameSessionDao;
import com.proxiad.hangmangame.model.ranking.GameRanking;
import com.proxiad.hangmangame.model.ranking.RankingModel;
import com.proxiad.hangmangame.model.ranking.RankingRepository;
import com.proxiad.hangmangame.model.statistic.GameResult;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import com.proxiad.hangmangame.model.statistic.GameStatisticRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import static com.proxiad.hangmangame.logic.game.GameConstants.UNKNOWN_LETTER_SYMBOL;
import static com.proxiad.hangmangame.model.statistic.GameStatisticSpecifications.haveWonGamesForLastNDays;
import static com.proxiad.hangmangame.web.ControllerConstants.INVALID_GAME_MSG;

@Service
@AllArgsConstructor
@Slf4j
public class RankingServiceImpl implements RankingService {

  private final GameStatisticRepository gameStatRepo;

  private final GameSessionDao gameSessionDao;

  private final RankingRepository rankingRepository;

  @Transactional
  @Override
  public void createRankingForPlayer(String gameId, String playerName)
      throws InvalidGameSessionException {
    GameSession gameSession =
        gameSessionDao
            .get(gameId)
            .orElseThrow(
                () -> new InvalidGameSessionException(String.format(INVALID_GAME_MSG, gameId)));

    GameStatistic newGameStatistic = new GameStatistic(GameResult.LOSS);
    newGameStatistic.setGameCompletionDate(new Date(System.currentTimeMillis()));

    boolean isGameLost = gameSession.getPuzzledWord().contains(UNKNOWN_LETTER_SYMBOL);
    boolean isGamerPresentInDB = rankingRepository.existsById(playerName);

    GameRanking finalRanking = new GameRanking(playerName, 0);

    if (!isGameLost) {
      newGameStatistic.setGameResult(GameResult.WIN);
      finalRanking.setTotalWins(1);
    }

    if (isGamerPresentInDB) {
      finalRanking = rankingRepository.getRankingByGamerName(playerName).get();
    }

    if (!isGameLost && isGamerPresentInDB) {
      int totalWins = finalRanking.getTotalWins();
      finalRanking.setTotalWins(++totalWins);
      newGameStatistic.setGameResult(GameResult.WIN);
    }

    newGameStatistic.setGameSession(gameSession);
    newGameStatistic.setGameRanking(finalRanking);
    gameStatRepo.save(newGameStatistic);
  }

  @Override
  public List<RankingModel> getTop10Players() {
    log.info("Initiated ranking requested for top 10 players->  ");
    return rankingRepository.findTop10ByOrderByTotalWinsDescGamerNameAsc().stream()
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }

  @Override
  public List<RankingModel> getTopNPlayersForLastNDays(int totalPlayers, int lastNDays) {

    List<GameStatistic> gameStats = gameStatRepo.findAll(haveWonGamesForLastNDays(lastNDays));
    Map<String, Integer> playersAndWinsMap =
        GameStatsDataExtractor.getTotalWinsForEachPlayer(gameStats);

    return playersAndWinsMap.entrySet().stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(totalPlayers)
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }
}
