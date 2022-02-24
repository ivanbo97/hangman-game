package com.proxiad.hangmangame.model.game;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GameMakeTryRequest {

  @NotBlank private String gameId;

  @Pattern(regexp = "[a-z]{1}")
  private String guessLetter;
}
