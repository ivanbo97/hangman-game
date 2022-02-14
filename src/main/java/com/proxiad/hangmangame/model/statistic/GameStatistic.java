package com.proxiad.hangmangame.model.statistic;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.ranking.GameRanking;

@Entity
@Table(name = "game_statistic")
public class GameStatistic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "game_result")
  @Enumerated(EnumType.STRING)
  private GameResult gameResult;

  @Column(name = "game_date")
  private Date gameCompletionDate;

  @OneToOne
  @JoinColumn(name = "id_game")
  private GameSession gameSession;

  @ManyToOne
  @JoinColumn(name = "gamer_id")
  private GameRanking gameRanking;

  public GameStatistic() {}

  public GameStatistic(GameResult gameResult) {
    this.gameResult = gameResult;
  }

  public void setGameResult(GameResult gameResult) {
    this.gameResult = gameResult;
  }

  public void setGameCompletionDate(Date gameCompletionDate) {
    this.gameCompletionDate = gameCompletionDate;
  }

  public void setGameSession(GameSession gameSession) {
    this.gameSession = gameSession;
  }

  public void setGameRanking(GameRanking gameRanking) {
    this.gameRanking = gameRanking;
  }

  public GameRanking getGameRanking() {
    return gameRanking;
  }

  public GameResult getGameResult() {
    return gameResult;
  }

  @Override
  public String toString() {
    return "GameStatistic [id="
        + id
        + ", "
        + gameRanking.getGamerName()
        + " , "
        + gameRanking.getTotalWins()
        + "]";
  }
}
