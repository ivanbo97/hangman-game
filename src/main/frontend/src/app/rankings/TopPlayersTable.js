import { useRanking } from "../api/RankingApi";
import Table from "react-bootstrap/Table";
import "./TopPlayersTable.css";
import TopPlayerTableRow from "./TopPlayerTableRow";
import { useState } from "react";
import { FormattedMessage } from "react-intl";

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
          <FormattedMessage id="topPlayersTable.name" />
          {shouldDisplayTop10List ? (
            <FormattedMessage id="topPlayersTable.name.ever" />
          ) : (
            <FormattedMessage id="topPlayersTable.name.lastmonth" />
          )}
        </h3>
        <Table>
          <thead>
            <tr>
              <th>
                <FormattedMessage id="topPlayersTable.th.playerName" />
              </th>
              <th>
                <FormattedMessage id="topPlayersTable.th.totalWins" />
              </th>
            </tr>
          </thead>
          <tbody>
            {playersList.map((player) => {
              return <TopPlayerTableRow key={player.playerName} {...player} />;
            })}
          </tbody>
        </Table>
      </div>
      <div className="alternative-ranking-url">
        <p style={urlStyle} onClick={handleShowRankingClick}>
          <FormattedMessage id="topPlayersTable.rankingType" />
          {shouldDisplayTop10List ? (
            <FormattedMessage id="topPlayersTable.name.lastmonth" />
          ) : (
            <FormattedMessage id="topPlayersTable.name.ever" />
          )}
        </p>
      </div>
    </>
  );
};

export default TopPlayersTable;
