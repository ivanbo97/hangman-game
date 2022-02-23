package com.proxiad.hangmangame.logic.game;

import java.util.List;
import com.proxiad.hangmangame.model.game.GameSession;

public interface GameSessionService {

  String getNewWord();

  GameSession startNewGame();

  GameSession makeTry(String gameId, String userGuess);

  GameSession getGameSessionById(String gameId);

  List<GameSession> getOnGoingGames();

  void deleteSessionById(String gameId);
}
