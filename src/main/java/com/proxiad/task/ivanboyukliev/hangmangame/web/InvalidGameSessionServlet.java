package com.proxiad.task.ivanboyukliev.hangmangame.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/invalidGameSessionHandler")
public class InvalidGameSessionServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processException(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processException(req, resp);
  }

  private void processException(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    ServletException servletException =
        (ServletException) req.getAttribute("jakarta.servlet.error.exception");
    req.getSession().setAttribute("errorMsg", servletException.getMessage());
    resp.sendRedirect("/hangman-game/invalidGameId.jsp");
  }
}
