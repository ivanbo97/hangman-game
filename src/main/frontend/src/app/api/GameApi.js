import fetchClient from "../utils/fetch-client";

export function startNewGame() {
  return fetchClient.post("/api/v1/games");
}
