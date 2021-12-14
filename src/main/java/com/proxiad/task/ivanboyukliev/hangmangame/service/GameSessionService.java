package com.proxiad.task.ivanboyukliev.hangmangame.service;

import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;

public interface GameSessionService {

  String getNewWord();

  void makeTry(GameSession gameSession, char userGuess);

  GameSession startNewGame();

}
