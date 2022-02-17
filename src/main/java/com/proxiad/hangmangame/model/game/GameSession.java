package com.proxiad.hangmangame.model.game;

import static com.proxiad.hangmangame.logic.game.GameConstants.BONUS_TRIES;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "game_session")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GameSession {

  @Id
  @Column(name = "id")
  private String gameId;

  @NotNull
  @Column(name = "word_to_guess")
  private String wordToGuess;

  @NotNull
  @Column(name = "puzzled_word")
  private String puzzledWord;

  @NotNull
  @Column(name = "tries_left")
  private int triesLeft;

  @NotNull
  @Column(name = "letters_to_guess_left")
  private int lettersToGuessLeft;

  @NotNull
  @Column(name = "guess_letters_encoded")
  private String lettersToBeGuessedEncoded;

  public GameSession() {}

  public GameSession(String wordToGuess) {
    this.wordToGuess = wordToGuess;
    this.lettersToGuessLeft = wordToGuess.length();
    this.triesLeft = this.lettersToGuessLeft + BONUS_TRIES;
  }
}
