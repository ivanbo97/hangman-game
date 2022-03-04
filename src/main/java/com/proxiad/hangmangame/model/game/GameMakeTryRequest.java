package com.proxiad.hangmangame.model.game;

import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GameMakeTryRequest {

  @Pattern(regexp = "[a-z]{1}")
  private String guessLetter;
}
