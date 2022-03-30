import { useEffect } from "react";
import GameInfo from "./GameInfo";
import GameGuess from "./GameGuess";
import { getGameById } from "../api/GameApi";
import { useParams, Redirect, useHistory } from "react-router-dom";
import toast from "react-hot-toast";
import { useCurrentUser } from "../api/UserApi";
import { createStatForGame } from "../api/StatisticApi";
import { useStateIfMounted } from "use-state-if-mounted";
import { useRanking } from "../api/RankingApi";
import { mutateRanking } from "../rankings/ranking-mutator";

const GamePlayPage = () => {
  
  const { gameId } = useParams();
  const [gameData, setGameData] = useStateIfMounted({});
  const history = useHistory();
  const { data: currentUser } = useCurrentUser();
  const isGamerRegistered = currentUser?.id;
  const showTop10EverList = true;
  const { data: playersList, mutate } = useRanking(showTop10EverList);

  useEffect(() => {
    getGameById(gameId)
      .then((data) => {
        setGameData(data);
      })
      .catch((e) => {
        toast.error(e.message);
        history.push("/");
      });
  }, [gameData.gameId]);

  const isGameFinished =
    gameData.lettersToGuessLeft === 0 || gameData.triesLeft === 0;

  if (isGameFinished && isGamerRegistered) {
    const playerName = currentUser.username;
    createStatForGame({
      gamerName: playerName,
      gameId: gameId,
    });
    mutate(
      mutateRanking({
        playersList: playersList,
        username: playerName,
        lettersToGuessLeft: gameData.lettersToGuessLeft,
      })
    );
    return <Redirect to={{ pathname: "/" }} />;
  }

  if (isGameFinished && !isGamerRegistered) {
    return (
      <Redirect
        push={false}
        to={{
          pathname: "/signup",
          state: { gameId: gameId },
        }}
      />
    );
  }

  return (
    <>
      {gameData.gameId ? (
        <div className="game-main-content">
          <GameInfo gameData={gameData} />
          <GameGuess setGameData={setGameData} gameId={gameData.gameId} />
        </div>
      ) : (
        <p>Loading game data...</p>
      )}
    </>
  );
};

export default GamePlayPage;
