import GameBtn from "./GameBtn";
import TopPlayersTable from "../rankings/TopPlayersTable";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import { startNewGame } from "../api/GameApi";

const GameStartPage = () => {
  const [isGameLoading, setGameLoading] = useState(false);
  const history = useHistory();

  const startGameOnClick = () => {
    setGameLoading(true);
    startNewGame().then((gameInfo) => {
      setGameLoading(false);
      history.push(`/games/${gameInfo.gameId}`, gameInfo);
    });
  };
  return (
    <>
      <GameBtn
        size="large"
        btnTextInit="New Game"
        btnTextClicked="Loading..."
        onClickHandler={startGameOnClick}
        btnDisabled={isGameLoading}
      />
      <TopPlayersTable showTop10Ever={true} />
    </>
  );
};

export default GameStartPage;
