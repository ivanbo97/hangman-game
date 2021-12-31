package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_USR_INPUT_LEN;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_USR_INPUT_TYPE;
import org.springframework.stereotype.Component;

@Component
public class UserInputValidator {

  public UserInputValidator() {}

  public void validateGameSessionExistance(GameSession gameSession) throws InvalidGameSessionException {

    if (gameSession == null) {
      throw new InvalidGameSessionException("Game session no longer exists!");
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
