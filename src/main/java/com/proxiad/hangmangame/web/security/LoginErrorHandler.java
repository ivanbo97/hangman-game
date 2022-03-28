package com.proxiad.hangmangame.web.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.proxiad.hangmangame.api.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class LoginErrorHandler {

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorMessage handleInvalidGameSessionException(AuthenticationException exception) {
    return new ErrorMessage(logError(exception));
  }

  private String logError(Exception exception) {
    String exceptionMessage = exception.getMessage();
    log.error(
        "Handling {} with message {}", exception.getClass().getSimpleName(), exceptionMessage);
    return exceptionMessage;
  }
}
