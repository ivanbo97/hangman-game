package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.BONUS_TRIES;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.SECRET_ENCODE_VAL;
import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.UNKNOWN_LETTER_SYMBOL;
import java.util.Objects;

public class GameSession {

  private String wordToGuess;
  private String puzzledWord;
  private String lettersToBeGuessed;
  private int triesLeft;
  private int lettersToGuessLeft;
  private String gameId;

  public GameSession() {}

  public GameSession(String wordToGuess) {
    this.wordToGuess = wordToGuess;
    this.lettersToGuessLeft = wordToGuess.length();
    this.puzzledWord = generatePuzzledWord(wordToGuess);
    this.triesLeft = this.lettersToGuessLeft + BONUS_TRIES;
    this.lettersToBeGuessed = encodeLettersToBeGuessed();
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

  public String getLettersToBeGuessed() {
    return lettersToBeGuessed;
  }

  private String generatePuzzledWord(String wordToGuess) {
    puzzledWord = UNKNOWN_LETTER_SYMBOL.repeat(wordToGuess.length());
    StringBuilder puzzledWordBuilder = new StringBuilder(puzzledWord);

    char firstLetter = wordToGuess.charAt(0);
    char lastLetter = wordToGuess.charAt(wordToGuess.length() - 1);
    char letterToCheck;

    for (int i = 0; i < wordToGuess.length(); i++) {
      letterToCheck = wordToGuess.charAt(i);
      if (letterToCheck == firstLetter || letterToCheck == lastLetter) {
        puzzledWordBuilder.setCharAt(i, letterToCheck);
        lettersToGuessLeft--;
      }
    }
    return puzzledWordBuilder.toString();
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public String getGameId() {
    return gameId;
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

  private String encodeLettersToBeGuessed() {
    int idx = 0;
    StringBuilder lettersToBeGuessedBuilder = new StringBuilder();

    for (char c : puzzledWord.toCharArray()) {
      if (c == '_') {
        char letterToGuess = wordToGuess.charAt(idx);
        lettersToBeGuessedBuilder.append((char) (letterToGuess - SECRET_ENCODE_VAL));
      }
      idx++;
    }

    String lettersToBeGuessedWithDuplicates = lettersToBeGuessedBuilder.toString();

    return lettersToBeGuessedWithDuplicates
        .chars()
        .distinct()
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
