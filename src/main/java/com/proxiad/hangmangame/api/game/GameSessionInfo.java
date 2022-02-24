package com.proxiad.hangmangame.api.game;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GameSessionInfo extends RepresentationModel<GameSessionInfo> {

  private String gameId;
  private String wordToGuess;
  private String puzzledWord;
  private Integer triesLeft;
  private Integer lettersToGuessLeft;
}
