import { useRanking } from "../api/RankingApi";
import Table from "react-bootstrap/Table";
import "./TopPlayersTable.css";
import TopPlayerTableRow from "./TopPlayerTableRow";
import { useState } from "react";

const TopPlayersTable = ({ showTop10Ever }) => {
  const [shouldDisplayTop10List, setDisplayTop10List] = useState(showTop10Ever);
  const { data: playersList } = useRanking(shouldDisplayTop10List);

  const urlStyle = {
    color: "blue",
    textDecoration: "underline",
    cursor: "pointer",
  };
  const handleShowRankingClick = () => {
    setDisplayTop10List(!shouldDisplayTop10List);
  };

  if (typeof playersList === "undefined") {
    return <p>Loading...</p>;
  }
  return (
    <>
      <div className="ranking-table">
        <h3 className="ranking-table-name">
          Top 10 Players {shouldDisplayTop10List ? "Ever" : "For Last Month"}
        </h3>
        <Table>
          <thead>
            <tr>
              <th>Player Name</th>
              <th>Total Wins</th>
            </tr>
          </thead>
          <tbody>
            {playersList.map((player, index) => {
              return <TopPlayerTableRow key={player.playerName} {...player} />;
            })}
          </tbody>
        </Table>
      </div>
      <div className="alternative-ranking-url">
        <p style={urlStyle} onClick={handleShowRankingClick}>
          Show Top 10 {shouldDisplayTop10List ? "For Last Month" : "Ever"}
        </p>
      </div>
    </>
  );
};

export default TopPlayersTable;
