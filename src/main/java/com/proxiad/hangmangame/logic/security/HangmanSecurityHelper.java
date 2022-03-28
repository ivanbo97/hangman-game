package com.proxiad.hangmangame.logic.security;

import java.util.Objects;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public final class HangmanSecurityHelper {

  private HangmanSecurityHelper() {}

  public static boolean isAuthenticated() {

    try {
      return Objects.nonNull(SecurityUtils.getSubject())
          && SecurityUtils.getSubject().isAuthenticated();
    } catch (Exception e) {
      return false;
    }
  }

  public static String fetchPrinciple() {
    return HangmanSecurityHelper.isAuthenticated()
        ? SecurityUtils.getSubject().getPrincipal().toString()
        : "no-principal";
  }

  public static void loginUser(String username, String password) {
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.login(token);
    // token.setRememberMe(true);
  }
}
