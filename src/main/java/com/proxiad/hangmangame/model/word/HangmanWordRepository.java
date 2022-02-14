package com.proxiad.hangmangame.model.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangmanWordRepository extends JpaRepository<HangmanWord, Integer> {
  HangmanWord getWordById(Integer id);
}
