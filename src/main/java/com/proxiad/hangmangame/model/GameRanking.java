package com.proxiad.hangmangame.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "game_ranking")
public class GameRanking {

  @Id
  @Column(name = "gamer_name")
  private String gamerName;

  @OneToMany(mappedBy = "gameRanking")
  private List<GameStatistic> gameStatistics = new ArrayList<>();

  @Column(name = "total_wins")
  private int totalWins;

  public GameRanking() {}

  public GameRanking(String gamerName, int totalWins) {
    this.gamerName = gamerName;
    this.totalWins = totalWins;
  }

  public String getGamerName() {
    return gamerName;
  }

  public void setGamerName(String gamerName) {
    this.gamerName = gamerName;
  }

  public List<GameStatistic> getGameStatistics() {
    return gameStatistics;
  }

  public int getTotalWins() {
    return totalWins;
  }

  public void setTotalWins(int totalWins) {
    this.totalWins = totalWins;
  }

  @Override
  public String toString() {
    return "GameRanking [gamerName="
        + gamerName
        + ", gameStatistics="
        + gameStatistics
        + ", totalWins="
        + totalWins
        + "]";
  }
}
