package com.proxiad.hangmangame.logic.game;

public class HangmanAppException extends RuntimeException {

  public HangmanAppException() {}

  public HangmanAppException(String message) {
    super(message);
  }

  public HangmanAppException(String message, Throwable cause) {
    super(message, cause);
  }

  public HangmanAppException(Throwable cause) {
    super(cause);
  }

  public HangmanAppException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
