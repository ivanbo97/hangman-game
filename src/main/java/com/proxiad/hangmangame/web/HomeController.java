package com.proxiad.hangmangame.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    System.out.println("Hello from homecontroller");
    return "forward:/index.html";
  }
}
