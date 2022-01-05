package com.proxiad.task.ivanboyukliev.hangmangame.web;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.GAME_PLAY_URL;
import java.io.IOException;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(GAME_PLAY_URL)
public class GamePlayServlet extends HttpServlet {

  private GameSessionService gameSessionService;

  @Override
  public void init() throws ServletException {
    gameSessionService = (GameSessionService) getServletContext().getAttribute("gameService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String gameId = extractGameId(req.getRequestURI());

    GameSession currentGameSession = (GameSession) getServletContext().getAttribute(gameId);

    gameSessionService.validateGameExistance(currentGameSession);

    req.setAttribute("gameSessionObj", currentGameSession);
    req.getRequestDispatcher("/hangmanMainPage.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String userGuess = req.getParameter("enteredLetter").toLowerCase();
    String gameId = extractGameId(req.getRequestURI());

    GameSession gameSession = (GameSession) getServletContext().getAttribute(gameId);
    GameSession currentGameSession = gameSessionService.makeTry(gameSession, userGuess);

    if (currentGameSession.getLettersToGuessLeft() == 0) {
      req.getSession().setAttribute("result", "Well Done! You have seccessfully guessed the word [ "
          + currentGameSession.getWordToGuess() + " ]");
      getServletContext().removeAttribute(currentGameSession.getGameId());
      resp.sendRedirect("/hangman-game/finalResultPage.jsp");
      return;
    }

    if (currentGameSession.getTriesLeft() == 0) {
      req.getSession().setAttribute("result", "Game Over! No more tries left! The word was [ "
          + currentGameSession.getWordToGuess() + " ]");
      getServletContext().removeAttribute(currentGameSession.getGameId());
      resp.sendRedirect("/hangman-game/finalResultPage.jsp");
      return;
    }

    resp.sendRedirect("/hangman-game/games/" + currentGameSession.getGameId());
  }

  private String extractGameId(String reqUri) {
    String uri = reqUri.substring(1);
    return uri.substring(uri.indexOf("/", uri.indexOf("/") + 1) + 1, uri.length());
  }
}
