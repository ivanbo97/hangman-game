import { Form } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { makeGuess } from "../api/GameApi";
import GameBtn from "./GameBtn";

const GameGuess = ({ setGameData, gameId }) => {
  const { register, control, handleSubmit, reset } = useForm({
    defaultValues: { guessLetter: "" },
  });

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
        <GameBtn
          size={"small"}
          btnTextInit="Submit!"
          btnTextClicked="......."
          btnDisabled={isSubmitting}
        ></GameBtn>
      </Form>
    </>
  );
};

export default GameGuess;
