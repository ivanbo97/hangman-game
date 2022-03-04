package it;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.HypermediaWebClientConfigurer;

@Configuration
public class WebClientConfig {

  @Bean
  WebClientCustomizer hypermediaWebClientCustomizer(HypermediaWebClientConfigurer configurer) {
    return configurer::registerHypermediaTypes;
  }
}
