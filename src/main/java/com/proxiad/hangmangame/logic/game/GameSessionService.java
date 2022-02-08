package com.proxiad.hangmangame.logic.game;

import javax.servlet.ServletException;
import com.proxiad.hangmangame.model.GameSession;

public interface GameSessionService {

  String getNewWord();

  GameSession startNewGame();

  GameSession makeTry(String gameId, String userGuess) throws ServletException;

  GameSession getGameSessionById(String gameId) throws InvalidGameSessionException;

  void deleteSessionById(String gameId);
}
