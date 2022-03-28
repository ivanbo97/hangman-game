package com.proxiad.hangmangame.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.logic.security.ExistingUserException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(InvalidGameSessionException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleInvalidGameSessionException(InvalidGameSessionException exception) {
    return new ErrorMessage(logError(exception));
  }

  @ExceptionHandler(ExistingUserException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleExisitngUserException(ExistingUserException exception) {
    return new ErrorMessage(logError(exception));
  }

  private String logError(Exception exception) {
    String exceptionMessage = exception.getMessage();
    log.error(
        "Handling {} with message {}", exception.getClass().getSimpleName(), exceptionMessage);
    return exceptionMessage;
  }
}
