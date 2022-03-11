import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());

export function useTop10Players() {
  return useSWR("/api/v1/rankings/top10", fetcher, {
    refreshInterval: 3 * 60 * 1000,
  });
}
