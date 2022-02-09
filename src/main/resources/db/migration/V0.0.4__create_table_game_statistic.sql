CREATE TABLE game_statistic
  (
     `id`          BIGINT NOT NULL AUTO_INCREMENT,
     `id_game`     VARCHAR(265) NOT NULL,
     `game_result` VARCHAR (10),
     `game_date`   DATE,
     `gamer_id`    VARCHAR(30),
     PRIMARY KEY (`id`),
     FOREIGN KEY (`id_game`) REFERENCES `game_session` (`id`),
     FOREIGN KEY (`gamer_id`) REFERENCES `game_ranking` (`gamer_name`)
  );