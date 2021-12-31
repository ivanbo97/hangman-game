package com.proxiad.task.ivanboyukliev.hangmangame.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.proxiad.task.ivanboyukliev.hangmangame.util.RandomNumberGenerator;

@Repository
public class WordRepository {

  private List<String> dictionary;


  public WordRepository() {
    dictionary = new ArrayList<>();
    populateWithWords();
  }

  private void populateWithWords() {
    dictionary.add("kilobyte");
    dictionary.add("kilowatt");
    dictionary.add("stack");
    dictionary.add("localhost");
    dictionary.add("interface");
    dictionary.add("class");
    dictionary.add("lambda");
  }

  public String getWord() {
    return dictionary.get(RandomNumberGenerator.generateRandomNumber());
  }
}
