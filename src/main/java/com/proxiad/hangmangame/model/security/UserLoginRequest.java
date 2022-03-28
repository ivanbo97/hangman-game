package com.proxiad.hangmangame.model.security;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

  @NotBlank private String username;
  @NotBlank private String password;
}
