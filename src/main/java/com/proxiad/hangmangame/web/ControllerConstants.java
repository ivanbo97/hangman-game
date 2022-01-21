package com.proxiad.hangmangame.web;

public final class ControllerConstants {

  // Application end-points
  public static final String APP_BASE_URL = "/hangman-game";
  public static final String GAME_BASE_URL = "/games";
  public static final String GAME_PLAY_URL = "/games/*";

  // Game-related messages
  public static final String SUCCESS_MSG = "Well Done! You have successfully guessed the word [%s]";
  public static final String FAILURE_MSG = "No more tries left! The word was [%s]";
  public static final String INVALID_GAME_MSG = "Game Session [%s] no longer exists!";
  public static final String INVALID_USR_INPUT_LEN =
      "User is supposed to enter single character only!";
  public static final String INVALID_USR_INPUT_TYPE = "User is supposed to enter letters only!";
  public static final String INVALID_LETTER_MSG =
      "Please, enter only a single not capitalized letter!";

  // Page titles
  public static final String SUCCESS_PAGE_TITLE = "Congratualtions!";
  public static final String FAILURE_PAGE_TITLE = "Unsuccessful attempt!";
}
