import fetchClient from "../utils/fetch-client";

export function signUpUser(userData) {
  return fetchClient.post("/login", { body: JSON.stringify(userData) });
}
