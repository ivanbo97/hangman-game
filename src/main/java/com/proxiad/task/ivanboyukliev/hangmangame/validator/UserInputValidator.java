package com.proxiad.task.ivanboyukliev.hangmangame.validator;

import static com.proxiad.task.ivanboyukliev.hangmangame.util.ApplicationConstants.INVALID_USR_INPUT_LEN;
import static com.proxiad.task.ivanboyukliev.hangmangame.util.ApplicationConstants.INVALID_USR_INPUT_TYPE;
import org.springframework.stereotype.Component;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidUserInputException;
import jakarta.servlet.ServletContext;

@Component
public class UserInputValidator {

  public UserInputValidator() {}

  public void validateGameSessionId(ServletContext servletContext, String givenGameId)
      throws InvalidGameSessionException {

    if (servletContext.getAttribute(givenGameId) == null) {
      throw new InvalidGameSessionException(
          "Game session with ID : " + givenGameId + " does not exist!");
    }
  }

  public void validateSingleLetterInput(String userInput) throws InvalidUserInputException {

    if (userInput.length() != 1) {
      throw new InvalidUserInputException(INVALID_USR_INPUT_LEN);
    }

    char enteredChar = userInput.charAt(0);

    if (Character.isDigit(enteredChar)) {
      throw new InvalidUserInputException(INVALID_USR_INPUT_TYPE);
    }
  }
}
