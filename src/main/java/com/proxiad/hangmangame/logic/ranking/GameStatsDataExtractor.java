package com.proxiad.hangmangame.logic.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.proxiad.hangmangame.model.statistic.GameStatistic;

public class GameStatsDataExtractor {

  public static Map<String, Integer> getTotalWinsForEachPlayer(List<GameStatistic> gameStats) {
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
