import { useEffect, useState } from "react";
import GameInfo from "./GameInfo";
import GameGuess from "./GameGuess";
import { getGameById } from "../api/GameApi";
import { useParams } from "react-router-dom";
import { Redirect } from "react-router-dom";

const GamePlayPage = () => {
  const { gameId } = useParams();
  const [gameData, setGameData] = useState({});

  useEffect(() => {
    getGameById(gameId).then((data) => {
      setGameData(data);
    });
  }, [gameId]);

  if (gameData.lettersToGuessLeft === 0 || gameData.triesLeft === 0) {
    return <Redirect push to={{ pathname: "/stats", state: gameData }} />;
  }

  return (
    <>
      {gameData.gameId ? (
        <div>
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
