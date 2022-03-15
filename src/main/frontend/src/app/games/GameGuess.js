import { Button, Form } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { makeGuess } from "../api/GameApi";

const GameGuess = ({ setGameData, gameId }) => {
  const submitGuessBtnStyle = {
    fontSize: "15px",
    marginTop: "20px",
  };

  const { register, control, handleSubmit, reset } = useForm();

  const { isSubmitting } = useFormState({ control });

  const handleLetterSubmit = async (userInput) => {
    const newGameData = await makeGuess(userInput, gameId);
    setGameData(newGameData);
    reset();
  };
  return (
    <>
      <Form onSubmit={handleSubmit(handleLetterSubmit)}>
        {/* Should be refactored with FormattedMessage intl compliance */}
        <Form.Label>Make your guess: </Form.Label>
        <Form.Control
          {...register("guessLetter")}
          required
          name="guessLetter"
          type="text"
          placeholder="Guess letter"
          title="Enter a valid letter. Numbers, special characters or empty strings are not allowed."
          pattern="[a-z]{1}"
        />
        <Button
          className="game-btn "
          type="submit"
          style={submitGuessBtnStyle}
          disabled={isSubmitting}
        >
          Submit!
        </Button>
      </Form>
    </>
  );
};

export default GameGuess;
