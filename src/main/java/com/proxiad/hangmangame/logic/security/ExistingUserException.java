package com.proxiad.hangmangame.logic.security;

import com.proxiad.hangmangame.logic.game.HangmanAppException;

public class ExistingUserException extends HangmanAppException {

  private static final long serialVersionUID = 1384569565504901083L;

  public ExistingUserException(String message) {
    super(message);
  }
}
