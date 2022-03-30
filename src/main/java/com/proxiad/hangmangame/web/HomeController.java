package com.proxiad.hangmangame.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping({"/", "/signup", "/register", "/games/*", "/stats"})
  public String index() {
    return "forward:/index.html";
  }
}
