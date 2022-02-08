package com.proxiad.hangmangame.model;

public class RankingModel {

  private String playerName;
  private int totalWins;

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

  @Override
  public String toString() {
    return "RankingModel [playerName=" + playerName + ", totalWins=" + totalWins + "]";
  }
}
