package com.proxiad.hangmangame.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import com.proxiad.hangmangame.logic.ranking.RankingService;

@Controller
@RequestMapping("/")
public class RankingController {

  @Autowired RankingService rankingService;

  @GetMapping
  public String getTop10PlayersEver(Model model) {
    model.addAttribute("messageToPlayer", "Welcome To Hangman Game! Have Fun!");
    model.addAttribute("pageTitle", "Hangman Game");
    model.addAttribute("topPlayers", rankingService.getTop10Players());
    model.addAttribute("tableName", "Top 10 Players Ever");
    model.addAttribute("urlForAdditionalStats", "top10ForLast30Days");
    model.addAttribute("urlElementName", "Show Top 10 For Last 30 Days");
    return "rankingPage";
  }

  @GetMapping("/top10ForLast30Days")
  public String getTopNPlayersForLasNDays(Model model) {
    model.addAttribute("topPlayers", rankingService.getTopNPlayersForLastNDays(10, 30));
    model.addAttribute("tableName", "Top 10 Player For Last 30 Days");
    model.addAttribute("urlForAdditionalStats", "/hangman-game/");
    model.addAttribute("urlElementName", "Show Top 10 Ever");
    return "rankingPage";
  }
}
