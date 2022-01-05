package com.proxiad.task.ivanboyukliev.hangmangame.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class GameSessionRepository {

  private Map<String, GameSession> gameSessions = new HashMap<>();

  public GameSession getGameSessionById(String gameId) {
    return gameSessions.get(gameId);
  }

  public void saveGameSession(GameSession newSession) {
    gameSessions.put(newSession.getGameId(), newSession);
  }

  public void deleteSessionById(String gameId) {
    gameSessions.remove(gameId);
  }
}
