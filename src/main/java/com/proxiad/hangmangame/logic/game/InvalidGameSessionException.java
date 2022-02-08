package com.proxiad.hangmangame.logic.game;

import javax.servlet.ServletException;

public class InvalidGameSessionException extends ServletException {

  public InvalidGameSessionException() {
    super();
  }

  public InvalidGameSessionException(String message) {
    super(message);
  }

  public InvalidGameSessionException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidGameSessionException(Throwable cause) {
    super(cause);
  }
}
