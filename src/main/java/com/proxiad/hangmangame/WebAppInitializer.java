package com.proxiad.hangmangame;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(WebConfiguration.class);

    DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
    ServletRegistration.Dynamic servletRegistrtaion =
        servletContext.addServlet("applicationServlet", dispatcherServlet);
    servletRegistrtaion.setLoadOnStartup(1);
    servletRegistrtaion.addMapping("/");
  }
}
