package com.proxiad.hangmangame.model.game;

import java.util.Optional;

public interface GameSessionDao {

  Optional<GameSession> get(String gameId);

  void save(GameSession newSession);

  void delete(GameSession gameSession);

  void deleteById(String gameId);
}
