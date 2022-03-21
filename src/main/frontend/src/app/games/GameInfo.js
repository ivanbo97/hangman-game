import { FormattedMessage } from "react-intl";

const GameInfo = ({ gameData }) => {
  return (
    <>
      <h1>
        <FormattedMessage id="gameInfo.title" />
      </h1>
      <div className="game-info">
        <p>
          <FormattedMessage id="gameInfo.word" />
          {gameData.puzzledWord}
        </p>
        <p>
          <FormattedMessage id="gameInfo.tries" />
          {gameData.triesLeft}
        </p>
        <p>
          <FormattedMessage id="gameInfo.lettersToGuess" />
          {gameData.lettersToGuessLeft}
        </p>
      </div>
    </>
  );
};

export default GameInfo;
