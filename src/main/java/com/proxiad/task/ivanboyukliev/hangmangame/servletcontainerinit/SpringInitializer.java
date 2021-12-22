package com.proxiad.task.ivanboyukliev.hangmangame.servletcontainerinit;

import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import com.proxiad.task.ivanboyukliev.hangmangame.springconfig.SpringContainerConfigurator;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class SpringInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

    AnnotationConfigApplicationContext springContainer =
        new AnnotationConfigApplicationContext(SpringContainerConfigurator.class);

    GameSessionService gameService =
        (GameSessionService) springContainer.getBean("gameSessionServiceImpl");

    ctx.setAttribute("gameService", gameService);
  }

}