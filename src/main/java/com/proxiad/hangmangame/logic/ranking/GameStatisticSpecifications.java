package com.proxiad.hangmangame.logic.ranking;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.proxiad.hangmangame.model.GameRanking;
import com.proxiad.hangmangame.model.GameRanking_;
import com.proxiad.hangmangame.model.GameStatistic;
import com.proxiad.hangmangame.model.GameStatistic_;

public class GameRankingSpecifications {

  public static Specification<GameStatistic> havePlayedForTheLastNDays(int days) {

    return new Specification<GameStatistic>() {

      @Override
      public Predicate toPredicate(
          Root<GameStatistic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Join<GameStatistic, GameRanking> gameRanking = root.join(GameStatistic_.GAME_RANKING);

        LocalDate localDateXDaysBefore = LocalDate.now().minusDays(days);
        Date today = new Date(System.currentTimeMillis());
        Date dateXDaysAgo = Date.valueOf(localDateXDaysBefore);

        query.groupBy(gameRanking.get(GameRanking_.GAMER_NAME));

        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.desc(gameRanking.get(GameRanking_.TOTAL_WINS)));
        orderList.add(criteriaBuilder.asc(gameRanking.get(GameRanking_.GAMER_NAME)));
        query.orderBy(orderList);

        return criteriaBuilder.between(
            root.get(GameStatistic_.GAME_COMPLETION_DATE), dateXDaysAgo, today);
      }
    };
  }
}
