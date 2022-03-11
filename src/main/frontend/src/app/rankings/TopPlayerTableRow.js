const TopPlayerTableRow = ({ player }) => {
  return (
    <>
      <tr>
        <td>{player.playerName}</td>
        <td>{player.totalWins}</td>
      </tr>
    </>
  );
};

export default TopPlayerTableRow;
