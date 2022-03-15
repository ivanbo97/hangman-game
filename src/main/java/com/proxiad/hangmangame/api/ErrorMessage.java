package com.proxiad.hangmangame.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorMessage {

  @JsonProperty("error_message")
  private String message;
}
