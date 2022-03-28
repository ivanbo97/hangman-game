package com.proxiad.hangmangame.api;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proxiad.hangmangame.api.user.UserInfo;
import com.proxiad.hangmangame.api.user.UserInfoAssembler;
import com.proxiad.hangmangame.logic.security.HangmanSecurityHelper;
import com.proxiad.hangmangame.logic.security.UserService;
import com.proxiad.hangmangame.model.security.User;
import com.proxiad.hangmangame.model.security.UserRegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApi {

  private final UserService userService;
  private final UserInfoAssembler userInfoAssembler;

  @PostMapping
  public void registerUser(@Valid @RequestBody UserRegisterRequest newUser) {
    userService.save(newUser);
    // Log user in system
    HangmanSecurityHelper.loginUser(newUser.getUsername(), newUser.getPassword());
  }

  @GetMapping("/current")
  public ResponseEntity<UserInfo> getCurrentUser() {
    if (!HangmanSecurityHelper.isAuthenticated()) {
      return ResponseEntity.notFound().build();
    }

    String username = HangmanSecurityHelper.fetchPrinciple();
    Optional<User> currentUser = userService.getUserByUsername(username);
    return ResponseEntity.ok(userInfoAssembler.toModel(currentUser.get()));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserInfo> getUser(@PathVariable String userId) {
    Optional<User> user = userService.getUser(userId);

    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(userInfoAssembler.toModel(user.get()));
  }
}
