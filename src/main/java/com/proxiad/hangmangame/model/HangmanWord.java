package com.proxiad.hangmangame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "game_word")
public class HangmanWord {

  @Id @NotNull private Integer id;

  @Column(name = "content")
  private String content;

  public HangmanWord() {}

  public HangmanWord(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
