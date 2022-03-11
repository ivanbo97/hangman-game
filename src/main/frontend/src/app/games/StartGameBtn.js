import { useState } from "react";
import Button from "react-bootstrap/Button";
import { startNewGame } from "../api/GameApi";
import { useHistory } from "react-router-dom";
import "./StartGameBtn.css";

const StartGameBtn = () => {
  const [isGameLoading, setGameLoading] = useState(false);
  const history = useHistory();

  const handleOnClick = () => {
    setGameLoading(true);
    startNewGame().then((gameInfo) => {
      setGameLoading(false);
      history.push(`/game/${gameInfo.gameId}`, gameInfo);
    });
  };
  return (
    <>
      <Button
        className="start-btn"
        disabled={isGameLoading}
        onClick={!isGameLoading ? handleOnClick : null}
      >
        {isGameLoading ? "Game Loading..." : "New Game"}
      </Button>
    </>
  );
};

export default StartGameBtn;
