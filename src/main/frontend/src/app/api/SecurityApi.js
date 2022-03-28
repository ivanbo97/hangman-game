import fetchClient from "../utils/fetch-client";

export function singUpUser(userData) {
  return fetchClient.post("/login", { body: JSON.stringify(userData) });
}
