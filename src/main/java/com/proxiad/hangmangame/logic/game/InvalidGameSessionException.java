package com.proxiad.hangmangame.logic.game;

public class InvalidGameSessionException extends HangmanAppException {

  private static final long serialVersionUID = -6329348888392184519L;

  public InvalidGameSessionException(String message) {
    super(message);
  }
}
