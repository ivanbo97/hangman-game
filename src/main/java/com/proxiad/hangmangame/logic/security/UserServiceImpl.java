package com.proxiad.hangmangame.logic.security;

import java.util.Optional;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.security.User;
import com.proxiad.hangmangame.model.security.UserRegisterRequest;
import com.proxiad.hangmangame.model.security.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public void save(UserRegisterRequest userRequest) {

    if (getUserByUsername(userRequest.getUsername()).isPresent()) {
      throw new ExistingUserException(
          "[" + userRequest.getUsername() + "] is already in use. Please, try another username!");
    }

    User newUser = modelMapper.map(userRequest, User.class);
    userRepository.save(newUser);
  }

  @Override
  public Optional<User> getUser(String userId) {
    return userRepository.findById(userId);
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }
}
