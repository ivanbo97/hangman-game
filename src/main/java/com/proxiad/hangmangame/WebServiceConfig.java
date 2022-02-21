package com.proxiad.hangmangame;

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.proxiad.hangmangame.logic.ranking.RankingWebService;

@Configuration
public class WebServiceConfig {
  @Autowired private Bus bus;
  @Autowired private RankingWebService rankingWebService;

  @Bean
  public Endpoint endPoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, rankingWebService);
    endpoint.publish("/HangmanGameRankingWS");
    return endpoint;
  }
}
