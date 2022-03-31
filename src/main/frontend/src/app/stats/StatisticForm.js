import { Form } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { createStatForGame } from "../api/StatisticApi";
import { useHistory } from "react-router-dom";
import { Link } from "react-router-dom";
import { StatInputStyle } from "./StatInputStyle";

import GameBtn from "../games/GameBtn";
import { useRanking } from "../api/RankingApi";
import { FormattedMessage } from "react-intl";

const StatisticFrom = ({ location }) => {
  const gameData = location.state;
  const { register, control, handleSubmit } = useForm();
  const { isSubmitting } = useFormState({ control });
  const showTop10EverList = true;
  const { data: playersList, mutate } = useRanking(showTop10EverList);

  const history = useHistory();
  const sortDescByWins = (p1, p2) => p2.totalWins - p1.totalWins;

  if (!gameData) {
    return <Link to="/">There is no game data. Return to main page</Link>;
  }

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
      <h3>
        {isWinner ? (
          <FormattedMessage id="statisticForm.titleWin" />
        ) : (
          <FormattedMessage id="statisticForm.titleLoss" />
        )}
        {gameData.wordToGuess}
      </h3>
      <h3>
        <FormattedMessage id="statisticForm.nameEnterMsg" />
      </h3>
      <Form onSubmit={handleSubmit(handleStatSubmit)}>
        <Form.Control
          style={StatInputStyle}
          className="stat-input"
          {...register("gamerName")}
          required
          name="gamerName"
          type="text"
        />
        <GameBtn
          size={"small"}
          btnTextInit=" Send name"
          btnTextClicked="......."
          btnDisabled={isSubmitting}
        >
          <FormattedMessage id="statisticForm.btn" />
        </GameBtn>
      </Form>
      <Link to="/">
        <FormattedMessage id="statisticForm.mainPageLink" />
      </Link>
    </>
  );
};

export default StatisticFrom;
