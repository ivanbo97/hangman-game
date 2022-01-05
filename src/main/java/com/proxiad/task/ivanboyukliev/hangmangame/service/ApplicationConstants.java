package com.proxiad.task.ivanboyukliev.hangmangame.service;

public final class ApplicationConstants {

  public static final int INITIAL_TRIES = 6;
  public static final String UNKNOWN_LETTER_SYMBOL = "_";
  public static final int BONUS_TRIES = 2;
  public static final String INVALID_USR_INPUT_LEN =
      "User is supposed to enter single character only!";
  public static final String INVALID_USR_INPUT_TYPE = "User is supposed to enter letters only!";

  // Application end-points

  public static final String APP_BASE_URL = "/hangman-game";
  public static final String GAME_BASE_URL = "/games";
  public static final String GAME_PLAY_URL = "/games/*";


  public static final String SUCCESS_MSG = "Well Done! You have successfully guessed the word [%s]";
  public static final String FAILURE_MSG = "No more tries left! The word was [%s]";
}
