package com.proxiad.task.ivanboyukliev.hangmangame.servlet;

import static com.proxiad.task.ivanboyukliev.hangmangame.util.ApplicationConstants.APP_BASE_URL;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GamePlayServletTest {

  @Spy
  private GamePlayServlet gamePlayServlet;

  @Mock
  private GameSessionService gameSessionService;

  @Mock
  private HttpServletRequest servletRequest;

  @Mock
  private HttpServletResponse servletResponse;

  @Mock
  private RequestDispatcher requestDispatcher;

  @Mock
  private ServletContext servletContext;

  @Mock
  private ServletConfig servletConfig;

  @Mock
  private HttpSession httpSession;

  private static String gameId = "A123D456C";

  @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
  @MethodSource("supplyTestParameters")
  void doPost(int lettersToGuessLeft, int triesLeft, String redirectUrl)
      throws ServletException, IOException {

    // given
    GameSession returnedGameSession = new GameSession();
    returnedGameSession.setLettersToGuessLeft(lettersToGuessLeft);
    returnedGameSession.setTriesLeft(triesLeft);
    returnedGameSession.setGameId(gameId);
    given(gamePlayServlet.getServletConfig()).willReturn(servletConfig);
    given(gamePlayServlet.getServletContext()).willReturn(servletContext);
    given(servletContext.getAttribute("gameService")).willReturn(gameSessionService);
    given(servletRequest.getParameter(anyString())).willReturn("a");
    given(servletRequest.getRequestURI()).willReturn("fakeUrl");
    given(gameSessionService.makeTry(any(ServletContext.class), anyString(), anyString()))
        .willReturn(returnedGameSession);
    given(servletRequest.getSession()).willReturn(httpSession);
    // when
    gamePlayServlet.init();
    gamePlayServlet.doPost(servletRequest, servletResponse);

    // then
    then(servletResponse).should().sendRedirect(redirectUrl);
  }

  private static Stream<Arguments> supplyTestParameters() {
    return Stream.of(Arguments.of(0, 5, APP_BASE_URL + "/finalResultPage.jsp"),
        Arguments.of(5, 0, APP_BASE_URL + "/finalResultPage.jsp"),
        Arguments.of(5, 5, APP_BASE_URL + "/games/" + gameId));
  }
}
