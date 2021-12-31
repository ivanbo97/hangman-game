package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_USR_INPUT_LEN;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_USR_INPUT_TYPE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.service.InvalidUserInputException;
import com.proxiad.task.ivanboyukliev.hangmangame.service.UserInputValidator;

@ExtendWith(MockitoExtension.class)
class UserInputValidatorTest {

  private UserInputValidator inputValidator = new UserInputValidator();


  @Test
  void validateGameSessionExistanceTest() throws InvalidGameSessionException {

    // when, then
    assertThrows(InvalidGameSessionException.class,
        () -> inputValidator.validateGameSessionExistance(null));
    assertDoesNotThrow(
        () -> inputValidator.validateGameSessionExistance(new GameSession("example")));
  }

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void validateSingleLetterInputTest(String userInput, String errorMessage) {

    Exception exception = Assertions.assertThrows(InvalidUserInputException.class,
        () -> inputValidator.validateSingleLetterInput(userInput));

    assertEquals(exception.getMessage(), errorMessage);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of("aaaaa", INVALID_USR_INPUT_LEN),
        Arguments.of("7", INVALID_USR_INPUT_TYPE));
  }

  @Test
  void validInputTest() throws Exception {
    assertDoesNotThrow(() -> inputValidator.validateSingleLetterInput("z"), "Fail");
  }
}
