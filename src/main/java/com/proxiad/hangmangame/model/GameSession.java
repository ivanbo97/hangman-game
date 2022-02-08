package com.proxiad.hangmangame.model;

import static com.proxiad.hangmangame.logic.game.GameConstants.BONUS_TRIES;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_session")
public class GameSession {

  @Id
  @Column(name = "id")
  private String gameId;

  @Column(name = "word_to_guess")
  private String wordToGuess;

  @Column(name = "puzzled_word")
  private String puzzledWord;

  @Column(name = "tries_left")
  private int triesLeft;

  @Column(name = "letters_to_guess_left")
  private int lettersToGuessLeft;

  @Column(name = "guess_letters_encoded")
  private String lettersToBeGuessedEncoded;

  public GameSession() {}

  public GameSession(String wordToGuess) {
    this.wordToGuess = wordToGuess;
    this.lettersToGuessLeft = wordToGuess.length();
    this.triesLeft = this.lettersToGuessLeft + BONUS_TRIES;
  }

  public String getWordToGuess() {
    return wordToGuess;
  }

  public void setWordToGuess(String wordToGuess) {
    this.wordToGuess = wordToGuess;
  }

  public int getTriesLeft() {
    return triesLeft;
  }

  public void setTriesLeft(int triesLeft) {
    this.triesLeft = triesLeft;
  }

  public int getLettersToGuessLeft() {
    return lettersToGuessLeft;
  }

  public void setLettersToGuessLeft(int lettersToGuessLeft) {
    this.lettersToGuessLeft = lettersToGuessLeft;
  }

  public String getPuzzledWord() {
    return puzzledWord;
  }

  public void setPuzzledWord(String puzzledWord) {
    this.puzzledWord = puzzledWord;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public String getGameId() {
    return gameId;
  }

  public String getLettersToBeGuessedEncoded() {
    return lettersToBeGuessedEncoded;
  }

  public void setLettersToBeGuessedEncoded(String lettersToBeGuessedEncoded) {
    this.lettersToBeGuessedEncoded = lettersToBeGuessedEncoded;
  }

  @Override
  public String toString() {
    return "GameSession [wordToGuess="
        + wordToGuess
        + ", triesLeft="
        + triesLeft
        + ", lettersToGuessLeft="
        + lettersToGuessLeft
        + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(lettersToGuessLeft, triesLeft, wordToGuess);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    GameSession other = (GameSession) obj;
    return lettersToGuessLeft == other.lettersToGuessLeft
        && triesLeft == other.triesLeft
        && Objects.equals(wordToGuess, other.wordToGuess);
  }
}
