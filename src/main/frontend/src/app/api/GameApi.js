import fetchClient from "../utils/fetch-client";

export function startNewGame() {
  return fetchClient.post("/api/v1/games");
}

export function makeGuess(userGuess, gameId) {
  return fetchClient.put(`/api/v1/games/${gameId}`, {
    body: JSON.stringify(userGuess),
  });
}

export function getGameById(gameId) {
  return fetchClient.get(`/api/v1/games/${gameId}`);
}
