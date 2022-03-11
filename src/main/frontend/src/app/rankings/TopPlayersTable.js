import { useTop10Players } from "../api/RankingApi";
import Table from "react-bootstrap/Table";
import "./TopPlayersTable.css";
import TopPlayerTableRow from "./TopPlayerTableRow";

const TopPlayersTable = () => {
  const { data: playersList } = useTop10Players();

  if (typeof playersList === "undefined") {
    return null;
  }
  return (
    <>
      <div className="ranking-table">
        <Table>
          <thead>
            <tr>
              <th>Player Name</th>
              <th>Total Wins</th>
            </tr>
          </thead>
          <tbody>
            {playersList.map((player, index) => {
              return <TopPlayerTableRow key={index} player={player} />;
            })}
          </tbody>
        </Table>
      </div>
    </>
  );
};

export default TopPlayersTable;
