package com.proxiad.hangmangame.model.security;

import org.apache.shiro.realm.jdbc.JdbcRealm;

public class HangmanRealm extends JdbcRealm {
  public HangmanRealm() {
    setSaltStyle(SaltStyle.EXTERNAL);
  }
}
