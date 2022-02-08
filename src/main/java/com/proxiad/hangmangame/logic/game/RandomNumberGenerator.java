package com.proxiad.hangmangame.logic.game;

public class RandomNumberGenerator {
  public static final int MAX_NUMBER = 6;

  public static final int MIN_NUMBER = 1;

  public static int generateRandomNumber() {
    return (int) ((Math.random() * (MAX_NUMBER - MIN_NUMBER)) + MIN_NUMBER);
  }
}
