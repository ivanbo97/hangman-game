package com.proxiad.task.ivanboyukliev.hangmangame;

import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class WebAppInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

    AnnotationConfigApplicationContext springContainer =
        new AnnotationConfigApplicationContext(WebConfiguration.class);

    GameSessionService gameService =
        (GameSessionService) springContainer.getBean(GameSessionService.class);

    ctx.setAttribute("gameService", gameService);
  }

}
