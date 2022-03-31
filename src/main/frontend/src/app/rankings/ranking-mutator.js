export const mutateRanking = async ({
  playersList,
  username,
  lettersToGuessLeft,
}) => {
  const sortDescByWins = (p1, p2) => p2.totalWins - p1.totalWins;
  const statsPlayerIdx = playersList.findIndex(
    (player) => player.playerName === username
  );
  const isWinner = lettersToGuessLeft === 0;
  if (statsPlayerIdx > -1 && isWinner) {
    // player exists in cache, we can mutate data
    playersList[statsPlayerIdx].totalWins++;
    playersList.sort(sortDescByWins);
  }
  return playersList;
};
