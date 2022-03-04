package com.proxiad.hangmangame.api.game;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.proxiad.hangmangame.model.game.GameSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;

@Component
public class GameSessionInfoAssembler
    extends RepresentationModelAssemblerSupport<GameSession, GameSessionInfo> {

  private TypeMap<GameSession, GameSessionInfo> gameToGameInfoMapper =
      new ModelMapper().createTypeMap(GameSession.class, GameSessionInfo.class);

  public GameSessionInfoAssembler() {
    super(GameSessionApi.class, GameSessionInfo.class);
  }

  @Override
  public GameSessionInfo toModel(GameSession gameSession) {
    var gameSessionInfo = gameToGameInfoMapper.map(gameSession);

    gameSessionInfo.add(
        linkTo(methodOn(GameSessionApi.class).getGame(gameSessionInfo.getGameId())).withSelfRel());

    return gameSessionInfo;
  }
}
