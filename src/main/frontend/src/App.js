import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import "./App.css";
import GamePlayPage from "./app/games/GamePlayPage";
import GameStartPage from "./app/games/GameStartPage";
function App() {
  return (
    <div className="App">
      {/* <p>Here will bee StartNewGameBtn Component</p>
      <p>Here will be TopPlayerList Component</p>
      <GameStartPage /> */}

      <Router>
        <Switch>
          <Route exact path="/" component={GameStartPage} />
          <Route exact path="/game/:gameId" component={GamePlayPage} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
