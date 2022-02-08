package com.proxiad.hangmangame.logic.game;

import java.util.Optional;
import com.proxiad.hangmangame.model.GameSession;

public interface GameSessionDao {

  Optional<GameSession> get(String gameId);

  void save(GameSession newSession);

  void delete(GameSession gameSession);

  void deleteById(String gameId);
}
