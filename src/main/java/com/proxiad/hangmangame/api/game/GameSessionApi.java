package com.proxiad.hangmangame.api.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.proxiad.hangmangame.api.BadRequestException;
import com.proxiad.hangmangame.logic.game.GameSessionService;
import com.proxiad.hangmangame.logic.game.InvalidGameSessionException;
import com.proxiad.hangmangame.model.game.GameMakeTryRequest;
import com.proxiad.hangmangame.model.game.GameSession;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameSessionApi {

  private final GameSessionService gameService;

  private final GameSessionInfoAssembler gameInfoAssembler;

  @GetMapping(
      value = "/ongoing",
      produces = {"application/hal+json"})
  @Operation(summary = "Lists all ongoing games")
  public ResponseEntity<CollectionModel<GameSessionInfo>> getOngoingGames() {

    List<GameSession> ongoingGames = gameService.getOnGoingGames();
    Link startNewGameLink =
        linkTo(methodOn(GameSessionApi.class).startNewGame()).withRel("startNewGame");
    return ResponseEntity.ok(
        gameInfoAssembler.toCollectionModel(ongoingGames).add(startNewGameLink));
  }

  @GetMapping(value = "/{gameId}", produces = "application/hal+json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get game by its id")
  public GameSessionInfo getGame(@PathVariable String gameId) {

    GameSession game = gameService.getGameSessionById(gameId);
    return gameInfoAssembler.toModel(game);
  }

  @PutMapping(value = "/{gameId}", produces = "application/hal+json")
  @Operation(summary = "Make a guess on a particular game with a particular letter")
  public ResponseEntity<GameSessionInfo> makeTry(
      @PathVariable String gameId, @Valid @RequestBody GameMakeTryRequest makeTryRequest) {

    try {
      GameSession updateGameSession = gameService.makeTry(gameId, makeTryRequest);
      return ResponseEntity.ok(gameInfoAssembler.toModel(updateGameSession));
    } catch (InvalidGameSessionException e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Start new game")
  public ResponseEntity<GameSessionInfo> startNewGame() {

    GameSession newGame = gameService.startNewGame();
    GameSessionInfo gameInfo = gameInfoAssembler.toModel(newGame);
    URI selfUri = gameInfo.getLink(IanaLinkRelations.SELF).get().toUri();
    return ResponseEntity.created(selfUri).body(gameInfo);
  }
}
