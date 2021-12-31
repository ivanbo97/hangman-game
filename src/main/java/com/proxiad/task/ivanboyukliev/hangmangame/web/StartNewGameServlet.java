package com.proxiad.task.ivanboyukliev.hangmangame.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.START_NEW_GAME_URL;
import java.io.IOException;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(START_NEW_GAME_URL)
public class StartNewGameServlet extends HttpServlet {


  private GameSessionService gameSessionService;

  @Override
  public void init() throws ServletException {
    gameSessionService = (GameSessionService) getServletContext().getAttribute("gameService");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    GameSession newSession = gameSessionService.startNewGame();

    String gameId = req.getSession().getId();
    newSession.setGameId(gameId);

    getServletContext().setAttribute(gameId, newSession);
    resp.sendRedirect("games/" + gameId);
  }

}
