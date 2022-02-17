package com.proxiad.hangmangame.model.ranking;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.proxiad.hangmangame.model.statistic.GameStatistic;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "game_ranking")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GameRanking {

  @Id
  @Column(name = "gamer_name")
  private String gamerName;

  @OneToMany(mappedBy = "gameRanking")
  @Exclude
  private List<GameStatistic> gameStatistics = new ArrayList<>();

  @Column(name = "total_wins")
  private int totalWins;

  public GameRanking(String gamerName, int totalWins) {
    this.gamerName = gamerName;
    this.totalWins = totalWins;
  }
}
