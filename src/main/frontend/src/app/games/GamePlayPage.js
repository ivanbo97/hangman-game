import { useEffect, useState } from "react";
import GameInfo from "./GameInfo";
import GameGuess from "./GameGuess";
import { getGameById } from "../api/GameApi";
import { useParams, Redirect } from "react-router-dom";
import toast from "react-hot-toast";
import { useHistory } from "react-router-dom";

const GamePlayPage = () => {
  const { gameId } = useParams();
  const [gameData, setGameData] = useState({});
  const history = useHistory();

  useEffect(() => {
    getGameById(gameId)
      .then((data) => {
        setGameData(data);
      })
      .catch((e) => {
        toast.error(e.message);
        history.push("/");
      });
  });

  if (gameData.lettersToGuessLeft === 0 || gameData.triesLeft === 0) {
    return (
      <Redirect push={false} to={{ pathname: "/stats", state: gameData }} />
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
