package com.proxiad.task.ivanboyukliev.hangmangame.service;

import javax.servlet.ServletException;

public class InvalidUserInputException extends ServletException {

  public InvalidUserInputException() {
    super();
  }

  public InvalidUserInputException(String message) {
    super(message);
  }

  public InvalidUserInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidUserInputException(Throwable cause) {
    super(cause);
  }

}
