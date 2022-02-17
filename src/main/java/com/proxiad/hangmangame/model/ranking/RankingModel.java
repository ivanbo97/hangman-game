package com.proxiad.hangmangame.model.ranking;

import java.util.Map.Entry;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RankingModel {

  private String playerName;
  private int totalWins;

  public static RankingModel setRankings(GameRanking gameRanking) {
    RankingModel model = new RankingModel();
    model.setPlayerName(gameRanking.getGamerName());
    model.setTotalWins(gameRanking.getTotalWins());
    return model;
  }

  public static RankingModel setRankings(Entry<String, Integer> playerAndWinsEntry) {
    String playerName = playerAndWinsEntry.getKey();
    Integer totalWins = playerAndWinsEntry.getValue();
    return new RankingModel(playerName, totalWins);
  }
}
