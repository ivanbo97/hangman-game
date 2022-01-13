package com.proxiad.hangmangame.logic;

import javax.servlet.ServletException;

public interface GameSessionService {

  String getNewWord();

  GameSession startNewGame();

  GameSession makeTry(String gameId, String userGuess) throws ServletException;

  GameSession getGameSessionById(String gameId) throws InvalidGameSessionException;

  void deleteSessionById(String gameId);
}
