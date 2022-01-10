package com.proxiad.task.ivanboyukliev.hangmangame.service;

import static com.proxiad.task.ivanboyukliev.hangmangame.service.ApplicationConstants.INVALID_GAME_MSG;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionServiceImpl implements GameSessionService {

  @Autowired private WordRepository wordRepository;

  @Autowired private GameSessionRepository gameSessionsRepo;

  @Override
  public String getNewWord() {
    return wordRepository.getWord();
  }

  @Override
  public GameSession getGameSessionById(String gameId) throws InvalidGameSessionException {
    return validateSessionExistence(gameId);
  }

  @Override
  public void deleteSessionById(String gameId) {
    gameSessionsRepo.deleteSessionById(gameId);
  }

  @Override
  public GameSession makeTry(String gameId, String userGuess) throws ServletException {

    GameSession gameSession = validateSessionExistence(gameId);

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
    GameSession newSession = new GameSession(wordToGuess);
    newSession.setGameId(UUID.randomUUID().toString());
    gameSessionsRepo.saveGameSession(newSession);
    return newSession;
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

  private GameSession validateSessionExistence(String gameId) throws InvalidGameSessionException {
    Optional<GameSession> retrievedSession = gameSessionsRepo.getGameSessionById(gameId);
    if (retrievedSession.isEmpty()) {
      throw new InvalidGameSessionException(String.format(INVALID_GAME_MSG, gameId));
    }
    return retrievedSession.get();
  }
}
