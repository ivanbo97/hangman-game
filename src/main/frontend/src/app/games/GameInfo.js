const GameInfo = ({ gameData }) => {
  return (
    <>
      <h1>Current game session details</h1>
      <div className="game-info">
        <p>{`Current word: ${gameData.puzzledWord}`}</p>
        <p>{`Tries left: ${gameData.triesLeft}`}</p>
        <p>{`Number of letters to guess: ${gameData.lettersToGuessLeft}`}</p>
      </div>
    </>
  );
};

export default GameInfo;
