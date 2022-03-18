import { Form } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { createStatForGame } from "../api/StatisticApi";
import { useHistory } from "react-router-dom";
import { Link } from "react-router-dom";
import { StatInputStyle } from "./StatInputStyle";

import GameBtn from "../games/GameBtn";
import { useRanking } from "../api/RankingApi";

const StatisticFrom = ({ location }) => {
  const gameData = location.state;
  const { register, control, handleSubmit } = useForm();
  const { isSubmitting } = useFormState({ control });
  const showTop10EverList = true;
  const { data: playersList, mutate } = useRanking(showTop10EverList);

  const history = useHistory();
  const sortDescByWins = (p1, p2) => p2.totalWins - p1.totalWins;

  if (typeof gameData === "undefined") {
    return <Link to="/">There is no game data. Return to main page</Link>;
  }
  console.log(playersList);
  const isWinner = gameData.lettersToGuessLeft === 0;
  const handleStatSubmit = (userInput) => {
    createStatForGame({ ...userInput, gameId: gameData.gameId });

    const enteredPlayerIdx = playersList.findIndex(
      (player) => player.playerName === userInput.gamerName
    );
    if (enteredPlayerIdx > -1 && isWinner) {
      // player exists in cache, we can mutate data
      playersList[enteredPlayerIdx].totalWins++;
      playersList.sort(sortDescByWins);
      mutate(playersList);
    }
    history.replace("/");
  };

  return (
    <>
      {isWinner ? (
        <h3>
          Well Done! You have successfully guessed the word [
          {gameData.wordToGuess}]
        </h3>
      ) : (
        <h3>No more tries left! The word was [{gameData.wordToGuess}]</h3>
      )}
      <h3>You can enter name for keeping statistics:</h3>
      <Form onSubmit={handleSubmit(handleStatSubmit)}>
        {/* Should be refactored with FormattedMessage intl compliance */}
        <Form.Control
          style={StatInputStyle}
          className="stat-input"
          {...register("gamerName")}
          required
          name="gamerName"
          type="text"
          placeholder="Enter your name..."
        />

        <GameBtn
          size={"small"}
          btnTextInit=" Send name"
          btnTextClicked="......."
          btnDisabled={isSubmitting}
        ></GameBtn>
      </Form>
      <Link to="/">Back to main page</Link>
    </>
  );
};

export default StatisticFrom;
