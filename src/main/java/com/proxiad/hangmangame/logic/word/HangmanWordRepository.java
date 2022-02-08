package com.proxiad.hangmangame.logic.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.model.HangmanWord;

@Repository
public interface HangmanWordRepository extends JpaRepository<HangmanWord, Integer> {
  HangmanWord getWordById(Integer id);
}
