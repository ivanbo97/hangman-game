package com.proxiad.task.ivanboyukliev.hangmangame.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proxiad.task.ivanboyukliev.hangmangame.domain.GameSession;
import com.proxiad.task.ivanboyukliev.hangmangame.repository.WordRepository;

@Service
public class GameSessionServiceImpl implements GameSessionService {

  private WordRepository wordRepository;

  @Autowired
  public GameSessionServiceImpl(WordRepository wordRepository) {
    this.wordRepository = wordRepository;
  }

  @Override
  public String getNewWord() {
    return wordRepository.getWord();
  }

  public void makeTry(GameSession gameSession, char userGuess) {

    int numberOfLettersToGuess = gameSession.getLettersToGuessLeft();
    int triesLeft = gameSession.getTriesLeft();

    int lettersGuessedInThisAttempt = checkForGuessedLetters(gameSession, userGuess);

    numberOfLettersToGuess -= lettersGuessedInThisAttempt;
    gameSession.setLettersToGuessLeft(numberOfLettersToGuess);

    triesLeft -= 1;
    gameSession.setTriesLeft(triesLeft);

  }

  @Override
  public GameSession startNewGame() {
    String wordToGuess = getNewWord();
    return new GameSession(wordToGuess);
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
}
