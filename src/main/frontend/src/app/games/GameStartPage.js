import StartGameBtn from "./StartGameBtn";
import TopPlayersTable from "../rankings/TopPlayersTable";

const GameStartPage = () => {
  return (
    <>
      <StartGameBtn />
      <TopPlayersTable showTop10Ever={true} />
    </>
  );
};

export default GameStartPage;
