CREATE TABLE game_session
  (
     `id`                    VARCHAR(265) NOT NULL,
     `word_to_guess`         VARCHAR(30),
     `puzzled_word`          VARCHAR(30),
     `tries_left`            INT,
     `letters_to_guess_left` INT,
     `guess_letters_encoded` VARCHAR(30),
     PRIMARY KEY (`id`)
  );