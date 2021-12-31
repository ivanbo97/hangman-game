package com.proxiad.task.ivanboyukliev.hangmangame.validator;

import static com.proxiad.task.ivanboyukliev.hangmangame.util.ApplicationConstants.INVALID_USR_INPUT_LEN;
import static com.proxiad.task.ivanboyukliev.hangmangame.util.ApplicationConstants.INVALID_USR_INPUT_TYPE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.mockito.BDDMockito.given;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidUserInputException;
import jakarta.servlet.ServletContext;

@ExtendWith(MockitoExtension.class)
class UserInputValidatorTest {

  private UserInputValidator inputValidator = new UserInputValidator();
  @Mock
  private ServletContext servletContext;

  @Test
  void validateGameSessionIdTest() throws InvalidGameSessionException {
    // given
    given(servletContext.getAttribute("A123")).willReturn(null);
    given(servletContext.getAttribute("A1234")).willReturn("");

    // when, then
    assertThrows(InvalidGameSessionException.class,
        () -> inputValidator.validateGameSessionId(servletContext, "A123"));
    assertDoesNotThrow(() -> inputValidator.validateGameSessionId(servletContext, "A1234"));
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
