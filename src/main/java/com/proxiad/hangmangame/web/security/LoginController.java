package com.proxiad.hangmangame.web.security;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.proxiad.hangmangame.logic.security.HangmanSecurityHelper;
import com.proxiad.hangmangame.model.security.UserLoginRequest;

@Controller
public class LoginController {

  @PostMapping("/login")
  public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequest loginRequest) {
    HangmanSecurityHelper.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    return ResponseEntity.ok().build();
  }
}
