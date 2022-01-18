package com.proxiad.hangmangame.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.logic.GameSession;

@Repository
public class GameSessionRepository {

  private Map<String, GameSession> gameSessions = new HashMap<>();

  public Optional<GameSession> getGameSessionById(String gameId) {
    return Optional.ofNullable(gameSessions.get(gameId));
  }

  public void saveGameSession(GameSession newSession) {
    gameSessions.put(newSession.getGameId(), newSession);
  }

  public void deleteSessionById(String gameId) {
    gameSessions.remove(gameId);
  }
}