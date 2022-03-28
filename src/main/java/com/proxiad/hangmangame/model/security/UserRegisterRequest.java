package com.proxiad.hangmangame.model.security;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegisterRequest {

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank private String username;

  @NotBlank private String password;
}
