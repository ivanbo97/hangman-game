package com.proxiad.hangmangame.model.ranking;

import java.util.Map.Entry;
import com.proxiad.hangmangame.model.statistic.GameStatistic;

public class RankingModel {

  private String playerName;
  private int totalWins;

  public RankingModel() {}

  public RankingModel(String playerName, int totalWins) {
    this.playerName = playerName;
    this.totalWins = totalWins;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public int getTotalWins() {
    return totalWins;
  }

  public void setTotalWins(int totalWins) {
    this.totalWins = totalWins;
  }

  public static RankingModel setRankings(GameStatistic gameStat) {
    RankingModel model = new RankingModel();
    GameRanking ranking = gameStat.getGameRanking();
    model.setPlayerName(ranking.getGamerName());
    model.setTotalWins(ranking.getTotalWins());
    return model;
  }

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

  @Override
  public String toString() {
    return "RankingModel [playerName=" + playerName + ", totalWins=" + totalWins + "]";
  }
}
