import GameBtn from "./GameBtn";
import TopPlayersTable from "../rankings/TopPlayersTable";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import { startNewGame } from "../api/GameApi";
import useLocale from "../../i18n/LocaleProvider";
import { Dropdown, NavItem, NavLink } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { dbLang } from "../icons/icons";
import { FormattedMessage } from "react-intl";
import "./GameStartPage.css";

const GameStartPage = () => {
  const [isGameLoading, setGameLoading] = useState(false);
  const history = useHistory();
  const { locale, supportedLocales, changeLocale } = useLocale();

  const availableLangs = Object.entries(supportedLocales)
    .filter(([lang]) => lang !== locale)
    .map(([lang, { label }]) => (
      <Dropdown.Item key={lang} onClick={() => changeLocale(lang)}>
        {label}
      </Dropdown.Item>
    ));

  const startGameOnClick = async () => {
    setGameLoading(true);
    const newGame = await startNewGame();
    history.push(`/games/${newGame.gameId}`);
  };

  return (
    <>
      <div className="lang-dropdown">
        <Dropdown as={NavItem}>
          <Dropdown.Toggle as={NavLink}>
            <FontAwesomeIcon icon={dbLang} />
          </Dropdown.Toggle>
          <Dropdown.Menu align={{ lg: "right" }}>
            {availableLangs}
          </Dropdown.Menu>
        </Dropdown>
      </div>
      <GameBtn
        size="large"
        btnTextInit="New Game"
        btnTextClicked="Loading..."
        onClickHandler={startGameOnClick}
        btnDisabled={isGameLoading}
      >
        <FormattedMessage id="gameStartPage.gameBtn.initTxt" />
      </GameBtn>
      <TopPlayersTable showTop10Ever={true} />
    </>
  );
};

export default GameStartPage;
