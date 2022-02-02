package com.proxiad.hangmangame.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proxiad.hangmangame.logic.RandomNumberGenerator;

@Repository
public interface HangmanWordRepository extends JpaRepository<HangmanWord, Integer> {
  HangmanWord getWordById(Integer id);
}
