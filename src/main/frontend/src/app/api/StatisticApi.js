import fetchClient from "../utils/fetch-client";

export function createStatForGame(gamerData) {
  fetchClient.post("/api/v1/stats", {
    body: JSON.stringify(gamerData),
  });
}
