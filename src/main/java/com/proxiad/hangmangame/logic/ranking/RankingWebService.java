package com.proxiad.hangmangame.logic.ranking;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import com.proxiad.hangmangame.model.ranking.RankingModel;

@WebService(name = "HangmanGameRankingWS")
@SOAPBinding
public interface RankingWebService {

  @WebMethod(operationName = "getTop10Ever")
  List<RankingModel> getTop10Players();

  @WebMethod(operationName = "getTop10ForLastMonth")
  List<RankingModel> getTop10ForLastMonth();
}
