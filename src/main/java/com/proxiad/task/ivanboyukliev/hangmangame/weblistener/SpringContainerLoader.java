package com.proxiad.task.ivanboyukliev.hangmangame.weblistener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.proxiad.task.ivanboyukliev.hangmangame.service.GameSessionService;
import com.proxiad.task.ivanboyukliev.hangmangame.springconfig.SpringContainerConfigurator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SpringContainerLoader implements ServletContextListener {

  private AnnotationConfigApplicationContext springContainer;

  private GameSessionService gameSessionService;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    springContainer = new AnnotationConfigApplicationContext(SpringContainerConfigurator.class);
    gameSessionService = (GameSessionService) springContainer.getBean("gameSessionServiceImpl");
    sce.getServletContext().setAttribute("gameService", gameSessionService);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    springContainer.close();
  }


}
