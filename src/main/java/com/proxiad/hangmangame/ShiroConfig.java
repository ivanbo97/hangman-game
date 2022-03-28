package com.proxiad.hangmangame;

import javax.sql.DataSource;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
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

    //    realm.setUserRolesQuery(
    //        "select r.name"
    //            + "from `user` u"
    //            + "join user_role ur on ur.user_id = u.user_id"
    //            + "join role r on r.role_id = ur.role_id"
    //            + "where u.username = ?");
    //
    //    realm.setPermissionsQuery(
    //        "select p.permission"
    //            + "from role_permission p"
    //            + "join role r on r.role_id = p.role_id"
    //            + "where r.name = ?");
    //
    //    realm.setPermissionsLookupEnabled(true);
    return realm;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }
}
