package it.com.proxiad.hangmangame.api;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.proxiad.hangmangame.HangmanApplication;
import com.proxiad.hangmangame.api.ranking.GameRankingApi;
import com.proxiad.hangmangame.logic.ranking.RankingService;
import com.proxiad.hangmangame.model.ranking.RankingModel;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(GameRankingApi.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = HangmanApplication.class)
class GameRankingApiIT {

  @MockBean RankingService rankingService;

  @Autowired MockMvc mockMvc;

  static List<RankingModel> dummyRankings;

  @BeforeAll
  static void createRankings() {
    dummyRankings =
        Arrays.asList(
            new RankingModel("John Doe", 12),
            new RankingModel("Java Smith", 8),
            new RankingModel("Ruby Rails", 15),
            new RankingModel("Spring Framework", 11));
  }

  @Test
  void getTopPlayersEverTest() throws Exception {

    // given
    given(rankingService.getTop10Players()).willReturn(dummyRankings);

    // when, then
    mockMvc
        .perform(get("/api/v1/rankings/top10"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(dummyRankings.size())));
  }

  @Test
  void getTopPlayersForLastMonthTest() throws Exception {

    // given
    given(rankingService.getTopNPlayersForLastNDays(10, 30)).willReturn(dummyRankings);

    // when, then

    mockMvc
        .perform(get("/api/v1/rankings/top10-last-month"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(dummyRankings.size())));
  }
}
