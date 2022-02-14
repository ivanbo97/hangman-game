package com.proxiad.hangmangame.model.statistic;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GameStatisticSpecifications {

  public static Specification<GameStatistic> haveWonGamesForLastNDays(int days) {

    return new Specification<GameStatistic>() {

      @Override
      public Predicate toPredicate(
          Root<GameStatistic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        LocalDate localDateXDaysBefore = LocalDate.now().minusDays(days);
        Date today = new Date(System.currentTimeMillis());
        Date dateXDaysAgo = Date.valueOf(localDateXDaysBefore);

        Predicate predicateForGameWin =
            criteriaBuilder.equal(root.get(GameStatistic_.GAME_RESULT), GameResult.WIN);
        Predicate predicateForDate =
            criteriaBuilder.between(
                root.get(GameStatistic_.GAME_COMPLETION_DATE), dateXDaysAgo, today);

        return criteriaBuilder.and(predicateForGameWin, predicateForDate);
      }
    };
  }
}
