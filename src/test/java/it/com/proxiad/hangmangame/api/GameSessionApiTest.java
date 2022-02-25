package it.com.proxiad.hangmangame.api;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxiad.hangmangame.HangmanApplication;
import com.proxiad.hangmangame.api.game.GameSessionInfo;
import com.proxiad.hangmangame.model.game.GameMakeTryRequest;
import com.proxiad.hangmangame.model.game.GameSession_;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest(classes = HangmanApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
class GameSessionApiTest {

  @Autowired WebClient.Builder webClientBuilder;

  @Autowired ObjectMapper objectMapper;

  private WebClient webClient;

  private ResponseEntity<CollectionModel<GameSessionInfo>> ongoingGamesResponse;

  private static final String GAME_API_BASE_URL = "http://localhost:8080/hangman-game/api/v1/games";

  @PostConstruct
  public void init() {
    webClient = webClientBuilder.build();

    ongoingGamesResponse =
        webClient
            .get()
            .uri(GAME_API_BASE_URL + "/ongoing")
            .accept(MediaTypes.HAL_JSON)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CollectionModel<GameSessionInfo>>() {})
            .block();
  }

  @Test
  @DisplayName("Test if there are any ongoing games retrieved")
  void ongoingGamesExistTest() {
    Collection<GameSessionInfo> ongoingGames = ongoingGamesResponse.getBody().getContent();
    assertThat(ongoingGamesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(ongoingGames).isNotEmpty();
  }

  @Test
  @DisplayName("Test if the retrned games are really ongoing")
  void checkIfGamesAreReallyOngoing() {
    Collection<GameSessionInfo> ongoingGames = ongoingGamesResponse.getBody().getContent();
    Condition<GameSessionInfo> ongoingGameCondition =
        new Condition<>(
            game -> game.getTriesLeft() != 0 && game.getLettersToGuessLeft() != 0, "ongoing game");

    assertThat(ongoingGames).filteredOn(ongoingGameCondition).isNotEmpty();
  }

  @Test
  @DisplayName("Test if we get game by its id (using alreadey retrieved ongoing games)")
  void getGameByIdTest() {
    // Take random gameId from ongoing games
    String gameId = getRandomGameId(ongoingGamesResponse.getBody().getContent());

    ResponseEntity<GameSessionInfo> retrievedGameResponse =
        webClient
            .get()
            .uri(GAME_API_BASE_URL + "/" + gameId)
            .accept(MediaTypes.HAL_JSON)
            .retrieve()
            .toEntity(GameSessionInfo.class)
            .block();

    GameSessionInfo retrievedGame = retrievedGameResponse.getBody();
    assertThat(retrievedGame).isNotNull();
    assertThat(retrievedGame.getGameId()).isEqualTo(gameId);
  }

  @Test
  @DisplayName("Test if we can create new game")
  void startNewGameTest() {

    post(GAME_API_BASE_URL)
        .then()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .and()
        .assertThat()
        .body(GameSession_.TRIES_LEFT, greaterThan(0))
        .body(GameSession_.LETTERS_TO_GUESS_LEFT, greaterThan(0));
  }

  @Test
  @DisplayName("Test if we make try on game that is ongoing")
  void makeTryTest() throws JsonProcessingException {

    GameMakeTryRequest gameTry = new GameMakeTryRequest();
    Collection<GameSessionInfo> ongoingGames = ongoingGamesResponse.getBody().getContent();

    String randomOngoingGameId = getRandomGameId(ongoingGames);
    gameTry.setGameId(randomOngoingGameId);
    gameTry.setGuessLetter("a");

    Response response =
        given()
            .header("Content-Type", ContentType.JSON)
            .body(objectMapper.writeValueAsString(gameTry))
            .post(GAME_API_BASE_URL + "/" + randomOngoingGameId);

    String responseBody = response.getBody().asString();
    assertThat(responseBody).isNotBlank();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
  }

  private String getRandomGameId(Collection<GameSessionInfo> e) {

    Optional<GameSessionInfo> randomGame =
        e.stream().skip((int) (e.size() * Math.random())).findFirst();
    return randomGame.get().getGameId();
  }
}
