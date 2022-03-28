package com.proxiad.hangmangame.model.security;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findById(String userId);

  Optional<User> getUserByUsername(String username);
}
