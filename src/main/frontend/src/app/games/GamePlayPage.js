import { useEffect, useState } from "react";
import GameInfo from "./GameInfo";
import GameGuess from "./GameGuess";
import { getGameById } from "../api/GameApi";
import { useParams, Redirect } from "react-router-dom";
import toast from "react-hot-toast";
import { useHistory } from "react-router-dom";
import { useCurrentUser } from "../api/UserApi";
import { createStatForGame } from "../api/StatisticApi";
import { useStateIfMounted } from "use-state-if-mounted";

const GamePlayPage = () => {
  const { gameId } = useParams();
  const [gameData, setGameData] = useStateIfMounted({});
  const history = useHistory();
  const { data: currentUser } = useCurrentUser();
  console.log(currentUser);
  const isGamerRegistered = currentUser?.id;
  console.log("Rendering GamePlay page...");
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

  const sendStat = async () => {
    console.log("cuurent user on finish : " + currentUser.username);
    await createStatForGame({
      gamerName: currentUser.username,
      gameId: gameId,
    });
  };

  if (isGameFinished && isGamerRegistered) {
    sendStat();
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
