package com.proxiad.hangmangame.model.statistic;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameStatRequest {

  @NotBlank private String gamerName;
  @NotBlank private String gameId;
}
