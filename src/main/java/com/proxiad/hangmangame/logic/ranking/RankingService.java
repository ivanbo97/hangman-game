package com.proxiad.hangmangame.logic.ranking;

import java.util.List;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.model.ranking.RankingModel;

public interface RankingService {

  void createRankingForPlayer(String gameId, String playerName) throws InvalidGameSessionException;

  List<RankingModel> getTop10Players();

  List<RankingModel> getTopNPlayersForLastNDays(int totalPlayers, int lastNDays);
}
