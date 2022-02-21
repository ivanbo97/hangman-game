package com.proxiad.hangmangame.logic.game;

import static com.proxiad.hangmangame.logic.game.GameConstants.BONUS_TRIES;
import static com.proxiad.hangmangame.logic.game.GameConstants.SECRET_ENCODE_VAL;
import static com.proxiad.hangmangame.logic.game.GameConstants.UNKNOWN_LETTER_SYMBOL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.proxiad.hangmangame.model.game.GameSession;
import com.proxiad.hangmangame.model.game.GameSessionDao;
import com.proxiad.hangmangame.model.word.HangmanWordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GameSessionServiceImpl implements GameSessionService {

  private final HangmanWordRepository wordRepository;

  private final GameSessionDao gameSessionsDao;

  private static final String INVALID_GAME_MSG = "Game Session [%s] no longer exists!";

  @Override
  public String getNewWord() {
    return wordRepository.getWordById(RandomNumberGenerator.generateRandomNumber()).getContent();
  }

  @Override
  public GameSession getGameSessionById(String gameId) throws InvalidGameSessionException {
    return validateSessionExistence(gameId);
  }

  @Override
  public void deleteSessionById(String gameId) {
    gameSessionsDao.deleteById(gameId);
  }

  @Override
  public GameSession makeTry(String gameId, String userGuess) throws ServletException {

    log.info("User is making a guess with letter [{}] on game session [{}]", userGuess, gameId);
    GameSession gameSession = validateSessionExistence(gameId);

    if (gameSession.getLettersToGuessLeft() == 0 || gameSession.getTriesLeft() == 0) {
      throw new InvalidGameSessionException(String.format(INVALID_GAME_MSG, gameId));
    }

    int numberOfLettersToGuess = gameSession.getLettersToGuessLeft();
    int triesLeft = gameSession.getTriesLeft();
    char userInputLetter = userGuess.charAt(0);

    int lettersGuessedInThisAttempt = checkForGuessedLetters(gameSession, userInputLetter);

    numberOfLettersToGuess -= lettersGuessedInThisAttempt;
    gameSession.setLettersToGuessLeft(numberOfLettersToGuess);

    triesLeft -= 1;
    gameSession.setTriesLeft(triesLeft);
    log.info("Game Session details after user guess ->  " + gameSession);
    return gameSession;
  }

  @Override
  public GameSession startNewGame() {
    String wordToGuess = getNewWord();
    GameSession newSession = new GameSession(wordToGuess);
    newSession.setGameId(UUID.randomUUID().toString());

    String puzzledWord = generatePuzzledWord(wordToGuess);
    newSession.setPuzzledWord(puzzledWord);

    int unknownLettersCount =
        (int) puzzledWord.chars().filter(c -> c == UNKNOWN_LETTER_SYMBOL.charAt(0)).count();
    newSession.setLettersToGuessLeft(unknownLettersCount);
    newSession.setTriesLeft(unknownLettersCount + BONUS_TRIES);

    String lettersToBeGuessedEncoded = encodeLettersToBeGuessed(puzzledWord, wordToGuess);
    newSession.setLettersToBeGuessedEncoded(lettersToBeGuessedEncoded);
    gameSessionsDao.save(newSession);
    log.info("New game started, Game Details ->  " + newSession);
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

    // Update puzlledWord
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
    Optional<GameSession> retrievedSession = gameSessionsDao.get(gameId);
    if (retrievedSession.isEmpty()) {
      log.error("Game Session with id [" + gameId + "] provided by client is invalid");
      throw new InvalidGameSessionException(String.format(INVALID_GAME_MSG, gameId));
    }
    return retrievedSession.get();
  }

  private String generatePuzzledWord(String wordToGuess) {

    String puzzledWord = UNKNOWN_LETTER_SYMBOL.repeat(wordToGuess.length());
    StringBuilder puzzledWordBuilder = new StringBuilder(puzzledWord);

    char firstLetter = wordToGuess.charAt(0);
    char lastLetter = wordToGuess.charAt(wordToGuess.length() - 1);
    char letterToCheck;

    for (int i = 0; i < wordToGuess.length(); i++) {
      letterToCheck = wordToGuess.charAt(i);
      if (letterToCheck == firstLetter || letterToCheck == lastLetter) {
        puzzledWordBuilder.setCharAt(i, letterToCheck);
      }
    }
    return puzzledWordBuilder.toString();
  }

  private String encodeLettersToBeGuessed(String puzzledWord, String wordToGuess) {
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
