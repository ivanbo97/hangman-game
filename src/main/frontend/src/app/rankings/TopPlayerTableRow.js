const TopPlayerTableRow = ({ playerName, totalWins }) => {
  return (
    <>
      <tr>
        <td>{playerName}</td>
        <td>{totalWins}</td>
      </tr>
    </>
  );
};

export default TopPlayerTableRow;
