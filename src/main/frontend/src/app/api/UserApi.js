import fetchClient from "../utils/fetch-client";
import useSWR from "swr";

export function registerUser(newUser) {
  return fetchClient.post("/api/v1/users", {
    body: JSON.stringify(newUser),
  });
}

export function useCurrentUser() {
  return useSWR("/api/v1/users/current");
}
