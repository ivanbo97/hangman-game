package com.proxiad.hangmangame.logic.security;

import java.util.Optional;
import com.proxiad.hangmangame.model.security.User;
import com.proxiad.hangmangame.model.security.UserRegisterRequest;

public interface UserService {

  public void save(UserRegisterRequest userRequest);

  public Optional<User> getUser(String userId);

  public Optional<User> getUserByUsername(String username);
}
