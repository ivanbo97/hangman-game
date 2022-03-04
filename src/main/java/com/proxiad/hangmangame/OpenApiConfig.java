package com.proxiad.hangmangame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(applicationInfo());
  }

  private Info applicationInfo() {

    Contact contact =
        new Contact()
            .name("Ivan Boyukliev")
            .url("https://github.com/ivanbo97/hangman-game.git")
            .email("ivanboyuklievv@gmail.com");

    Map<String, Object> licenceExtensions = new HashMap<>();
    licenceExtensions.put("https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    License license =
        new License().name("Apache Licence Version 2.0").extensions(licenceExtensions);

    return new Info()
        .title("HANGMAN GAME")
        .description("Simple hangman game REST app based on Spring Boot")
        .contact(contact)
        .version("1.0.0")
        .license(license);
  }
}
