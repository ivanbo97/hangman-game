import StartGameBtn from "./StartGameBtn";
import TopPlayersList from "../rankings/TopPlayersTable";

const GameStartPage = () => {
  return (
    <>
      <StartGameBtn />
      <h3 className="ranking-table-name">Top 10 Players Ever</h3>
      <TopPlayersList />
    </>
  );
};

export default GameStartPage;
