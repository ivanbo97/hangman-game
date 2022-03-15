package com.proxiad.hangmangame.web;

import javax.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  private Log logger = LogFactory.getLog("ClientError");

  //  @ExceptionHandler(InvalidGameSessionException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public String handleInvalidGameSessionException(
  //      InvalidGameSessionException exception, Model model) {
  //    return processError(exception, model);
  //  }
  //
  //  @ExceptionHandler(ConstraintViolationException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public String handleInvalidUserInput(ConstraintViolationException exception, Model model) {
  //    return processError(exception, model);
  //  }
  //
  //  @ExceptionHandler(MethodArgumentNotValidException.class)
  //  public ResponseEntity<Void> handleMethodArgumentNotValidException(Exception e) {
  //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  //  }
  //
  //  private String processError(Exception exception, Model model) {
  //    String exceptionMessage = exception.getMessage();
  //    logger.error(exceptionMessage);
  //    model.addAttribute("errorMsg", exceptionMessage);
  //    return "error";
  //  }
}
