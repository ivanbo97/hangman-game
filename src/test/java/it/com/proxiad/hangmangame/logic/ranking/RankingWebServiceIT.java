package it.com.proxiad.hangmangame.logic.ranking;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.com.proxiad.hangmangame.logic.ranking.soapconsumer.HangmanGameRankingWS;
import it.com.proxiad.hangmangame.logic.ranking.soapconsumer.RankingModel;
import it.com.proxiad.hangmangame.logic.ranking.soapconsumer.RankingWebServiceImplService;

import static org.assertj.core.api.Assertions.assertThat;

class RankingWebServiceIT {

  private HangmanGameRankingWS webService;

  @BeforeEach
  private void init() {
    RankingWebServiceImplService webServiceClient = new RankingWebServiceImplService();
    webService = webServiceClient.getRankingWebServiceImplPort();
  }

  @Test
  void retrievedDataNotNullTest() {
    List<RankingModel> retrievedRankings = webService.getTop10Ever();
    assertNotNull(retrievedRankings);
  }

  @Test
  void testRetrievedRankingsSize() {
    assertThat(webService.getTop10Ever().size()).isLessThanOrEqualTo(10);
  }

  @Test
  void testRankingIsOrderedByWinsDesc() {
    List<RankingModel> retrievedRankings = webService.getTop10Ever();

    for (int i = 0; i < retrievedRankings.size() - 1; i++) {
      int rankingFirstWins = retrievedRankings.get(i).getTotalWins();
      int rankingSecondWins = retrievedRankings.get(i + 1).getTotalWins();
      assertThat(rankingFirstWins).isGreaterThanOrEqualTo(rankingSecondWins);
    }
  }

  @Test
  void getTop10ForLastMonthTest() {
    assertThat(webService.getTop10ForLastMonth().size()).isEqualTo(10);
  }
}
