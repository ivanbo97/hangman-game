import { Button, Form } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { createStatForGame } from "../api/StatisticApi";
import { useHistory } from "react-router-dom";
import { Link } from "react-router-dom";

const StatisticFrom = ({ location }) => {
  const gameData = location.state;
  const { register, control, handleSubmit } = useForm();
  const { isSubmitting } = useFormState({ control });
  const history = useHistory();

  if (typeof gameData === "undefined") {
    return <Link to="/">There is no game data. Return to main page</Link>;
  }

  const handleStatSubmit = async (userInput) => {
    createStatForGame({ ...userInput, gameId: gameData.gameId });
    history.replace("/");
  };
  return (
    <>
      {gameData.lettersToGuessLeft === 0 ? (
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
          {...register("gamerName")}
          required
          name="gamerName"
          type="text"
          placeholder="Enter your name..."
        />
        <Button className="game-btn " type="submit" disabled={isSubmitting}>
          Send name
        </Button>
      </Form>
      <Link to="/">Back to main page</Link>
    </>
  );
};

export default StatisticFrom;