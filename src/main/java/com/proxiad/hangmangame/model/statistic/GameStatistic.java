package com.proxiad.hangmangame.model.statistic;

import java.sql.Date;
import javax.persistence.CascadeType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "game_statistic")
@Getter
@Setter
@ToString
@NoArgsConstructor
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

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "gamer_id")
  private GameRanking gameRanking;

  public GameStatistic(GameResult gameResult) {
    this.gameResult = gameResult;
  }
}
