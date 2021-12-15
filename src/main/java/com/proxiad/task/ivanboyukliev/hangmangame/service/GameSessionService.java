package com.proxiad.task.ivanboyukliev.hangmangame.service;

import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public interface GameSessionService {

  String getNewWord();

  GameSession startNewGame();

  GameSession makeTry(ServletContext servletContext, String requestUri, String userGuess)
      throws ServletException;

  GameSession reloadGame(ServletContext servletContext, String userRequest)
      throws InvalidGameSessionException;
}
