import Button from "react-bootstrap/Button";
import "./GameBtn.css";
import PropTypes from "prop-types";

const GameBtn = ({
  size,
  onClickHandler,
  btnDisabled,
  children,
}) => {
  return (
    <>
      <Button
        onClick={onClickHandler}
        disabled={btnDisabled}
        type="submit"
        className={["game-btn", `game-btn--${size}`].join(" ")}
      >
        {children}
      </Button>
    </>
  );
};

GameBtn.propTypes = {
  onClickHandler: PropTypes.func,
  btnDisabled: PropTypes.bool,
  size: PropTypes.oneOf(["large", "small"]),
};

export default GameBtn;
