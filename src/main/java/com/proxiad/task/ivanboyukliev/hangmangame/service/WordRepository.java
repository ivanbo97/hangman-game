package com.proxiad.task.ivanboyukliev.hangmangame.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

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
