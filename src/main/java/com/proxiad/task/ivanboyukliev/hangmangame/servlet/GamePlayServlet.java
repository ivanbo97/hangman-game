package com.proxiad.task.ivanboyukliev.hangmangame.servlet;

import java.io.IOException;
import java.util.Optional;
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/games/*")
public class GamePlayServlet extends HttpServlet {

  private GameSessionService gameSessionService;

  @Override
  public void init() throws ServletException {
    gameSessionService = (GameSessionService) getServletContext().getAttribute("gameService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String gameId = getGameId(req);

    Optional<GameSession> currentGameSession = checkForGameSessionExistence(gameId);

    if (currentGameSession.isEmpty()) {
      throw new InvalidGameSessionException(
          "Game session with ID : " + gameId + " does not exist!");
    }

    req.setAttribute("gameSessionObj", currentGameSession.get());
    req.getRequestDispatcher("/hangmanMainPage.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String gameId = getGameId(req);

    Optional<GameSession> currentGameSession = checkForGameSessionExistence(gameId);

    if (currentGameSession.isEmpty()) {
      throw new InvalidGameSessionException("Game session with ID : " + gameId + "does not exist!");
    }

    GameSession currentGameSessionObj = currentGameSession.get();
    char receivedLetter = req.getParameter("enteredLetter").toLowerCase().charAt(0);

    gameSessionService.makeTry(currentGameSessionObj, receivedLetter);

    if (currentGameSessionObj.getLettersToGuessLeft() == 0) {
      req.getSession().setAttribute("result", "Well Done! You have seccessfully guessed the word!");
      resp.sendRedirect("/hangman-game/finalResultPage.jsp");
      return;
    }

    if (currentGameSessionObj.getTriesLeft() == 0) {
      req.getSession().setAttribute("result", "Game Over! No more tries left!");
      resp.sendRedirect("/hangman-game/finalResultPage.jsp");
      return;
    }

    resp.sendRedirect("/hangman-game/games/" + gameId);
  }

  private String getGameId(HttpServletRequest req) {
    String uri = req.getRequestURI().substring(1);
    return uri.substring(uri.indexOf("/", uri.indexOf("/") + 1) + 1, uri.length());
  }

  private Optional<GameSession> checkForGameSessionExistence(String gameId) {
    return Optional.ofNullable((GameSession) getServletContext().getAttribute(gameId));
  }
}
