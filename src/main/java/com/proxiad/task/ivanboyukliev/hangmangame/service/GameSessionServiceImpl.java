package com.proxiad.task.ivanboyukliev.hangmangame.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.exception.InvalidGameSessionException;
import com.proxiad.task.ivanboyukliev.hangmangame.repository.WordRepository;
import com.proxiad.task.ivanboyukliev.hangmangame.validator.UserInputValidator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Service
public class GameSessionServiceImpl implements GameSessionService {

  private WordRepository wordRepository;
  private UserInputValidator inputValidator;

  @Autowired
  public GameSessionServiceImpl(WordRepository wordRepository, UserInputValidator inputValidator) {
    this.wordRepository = wordRepository;
    this.inputValidator = inputValidator;
  }

  @Override
  public String getNewWord() {
    return wordRepository.getWord();
  }


  @Override
  public GameSession makeTry(ServletContext servletContext, String requestUri, String userGuess)
      throws ServletException {

    String gameId = extractGameId(requestUri);

    inputValidator.validateSingleLetterInput(userGuess);
    inputValidator.validateGameSessionId(servletContext, gameId);

    GameSession gameSession = (GameSession) servletContext.getAttribute(gameId);

    int numberOfLettersToGuess = gameSession.getLettersToGuessLeft();
    int triesLeft = gameSession.getTriesLeft();
    char userInputLetter = userGuess.charAt(0);

    int lettersGuessedInThisAttempt = checkForGuessedLetters(gameSession, userInputLetter);

    numberOfLettersToGuess -= lettersGuessedInThisAttempt;
    gameSession.setLettersToGuessLeft(numberOfLettersToGuess);

    triesLeft -= 1;
    gameSession.setTriesLeft(triesLeft);

    return gameSession;
  }

  @Override
  public GameSession startNewGame() {
    String wordToGuess = getNewWord();
    return new GameSession(wordToGuess);
  }

  @Override
  public GameSession reloadGame(ServletContext servletContext, String userRequest)
      throws InvalidGameSessionException {
    String gameId = extractGameId(userRequest);
    inputValidator.validateGameSessionId(servletContext, gameId);
    return (GameSession) servletContext.getAttribute(gameId);
  }

  private int checkForGuessedLetters(GameSession gameSession, char userInputLetter) {

    String wordToGuess = gameSession.getWordToGuess();
    long guessedLtrsOccurCnt = wordToGuess.chars().filter(ch -> ch == userInputLetter).count();

    String previousPuzzledWord = gameSession.getPuzzledWord();

    if (guessedLtrsOccurCnt == 0) {
      return 0;
    }

    List<Integer> indexesOfOccurrence = findIndexesOfOccurence(wordToGuess, userInputLetter);
    String newPuzzledWord =
        replaceCharAtIdx(previousPuzzledWord, indexesOfOccurrence, userInputLetter);

    if (previousPuzzledWord.equals(newPuzzledWord)) {
      // User has entered the same symbol as one of his previous attempts
      // therefore he hasn't guessed anything new
      return 0;
    }

    // Update puzlledWord in servlet context
    gameSession.setPuzzledWord(newPuzzledWord);

    // return number of guessed letters
    return indexesOfOccurrence.size();
  }

  private List<Integer> findIndexesOfOccurence(String word, char searchedChar) {
    int index = word.indexOf(searchedChar);
    List<Integer> idxsOfOccurence = new ArrayList<>();

    while (index >= 0) {
      idxsOfOccurence.add(index);
      index = word.indexOf(searchedChar, index + 1);
    }
    return idxsOfOccurence;
  }

  private String replaceCharAtIdx(String word, List<Integer> idxsForReplace, char replacingSymbol) {
    String resultWord = word;
    for (int idx : idxsForReplace) {
      char[] currentPuzzledChars = resultWord.toCharArray();
      currentPuzzledChars[idx] = replacingSymbol;
      resultWord = String.valueOf(currentPuzzledChars);
    }
    return resultWord;
  }

  private String extractGameId(String reqUri) {
    String uri = reqUri.substring(1);
    return uri.substring(uri.indexOf("/", uri.indexOf("/") + 1) + 1, uri.length());
  }

}
