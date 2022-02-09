package com.proxiad.hangmangame.logic.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.logic.game.GameSessionDao;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.logic.statistic.GameStatisticRepositorty;
import com.proxiad.hangmangame.model.GameRanking;
import com.proxiad.hangmangame.model.GameResult;
import com.proxiad.hangmangame.model.GameSession;
import com.proxiad.hangmangame.model.GameStatistic;
import com.proxiad.hangmangame.model.RankingModel;
import java.sql.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import static com.proxiad.hangmangame.logic.game.GameConstants.UNKNOWN_LETTER_SYMBOL;
import static com.proxiad.hangmangame.logic.statistic.GameStatisticSpecifications.haveWonGamesForLastNDays;
import static com.proxiad.hangmangame.web.ControllerConstants.INVALID_GAME_MSG;

@Service
public class RankingServiceImpl implements RankingService {

  @Autowired private GameStatisticRepositorty gameStatRepo;

  @Autowired private GameSessionDao gameSessionDao;

  @Autowired private RankingRepository rankingRepository;

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
    }

    if (isGamerPresentInDB) {
      finalRanking = rankingRepository.getRankingByGamerName(playerName).get();
    } else {
      rankingRepository.save(finalRanking);
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
    return rankingRepository.findTop10ByOrderByTotalWinsDescGamerNameAsc().stream()
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }

  @Override
  public List<RankingModel> getTopNPlayersForLastNDays(int totalPlayers, int lastNDays) {

    List<GameStatistic> gameStats = gameStatRepo.findAll(haveWonGamesForLastNDays(lastNDays));
    Map<String, Integer> playersAndWinsMap = getTotalWinsForEachPlayer(gameStats);

    return playersAndWinsMap.entrySet().stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(totalPlayers)
        .map(RankingModel::setRankings)
        .collect(Collectors.toList());
  }

  private Map<String, Integer> getTotalWinsForEachPlayer(List<GameStatistic> gameStats) {

    Map<String, Integer> playersAndWinsMap = new HashMap<>();

    for (GameStatistic stat : gameStats) {

      String playerName = stat.getGameRanking().getGamerName();
      Integer winsCount = playersAndWinsMap.get(playerName);
      if (winsCount == null) {
        playersAndWinsMap.put(playerName, 1);
        continue;
      }
      playersAndWinsMap.put(playerName, winsCount + 1);
    }
    return playersAndWinsMap;
  }
}
