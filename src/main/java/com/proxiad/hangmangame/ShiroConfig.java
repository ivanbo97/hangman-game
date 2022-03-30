package com.proxiad.hangmangame;

import javax.sql.DataSource;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {

    DefaultShiroFilterChainDefinition filter = new DefaultShiroFilterChainDefinition();
    filter.addPathDefinition("/api/v1/stats", "authc");
    filter.addPathDefinition("/**", "anon");
    return filter;
  }

  @Bean
  public Realm realm(DataSource dataSource) {

    JdbcRealm realm = new JdbcRealm();
    realm.setDataSource(dataSource);
    realm.setAuthenticationQuery(" select password from `user` where username = ?");
    return realm;
  }
}
