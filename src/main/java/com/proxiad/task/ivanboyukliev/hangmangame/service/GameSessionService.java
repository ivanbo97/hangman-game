package com.proxiad.task.ivanboyukliev.hangmangame.service;

import jakarta.servlet.ServletException;

public interface GameSessionService {

  String getNewWord();

  GameSession startNewGame();

  GameSession makeTry(GameSession gameSession, String userGuess) throws ServletException;

  void validateGameExistance(GameSession gameSession) throws InvalidGameSessionException;
}
