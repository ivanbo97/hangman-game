import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());
const top10PlayersEverUrl = "/api/v1/rankings/top10";
const top10PlayersLastMonthUrl = "/api/v1/rankings/top10-last-month";

export function useRanking(shouldDisplayTop10EverList) {
  const apiUrl = shouldDisplayTop10EverList
    ? top10PlayersEverUrl
    : top10PlayersLastMonthUrl;
  return useSWR(apiUrl, fetcher, {
    refreshInterval: 3 * 60 * 1000,
  });
}
