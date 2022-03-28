import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import "./App.css";
import GamePlayPage from "./app/games/GamePlayPage";
import GameStartPage from "./app/games/GameStartPage";
import RegisterForm from "./app/RegisterForm";
import SignUpForm from "./app/SignUpForm";
import StatisticFrom from "./app/stats/StatisticForm";
function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route exact path="/" component={GameStartPage} />
          <Route path="/games/:gameId" component={GamePlayPage} />
          <Route path="/stats" component={StatisticFrom} />
          <Route path="/register" component={RegisterForm} />
          <Route path="/signup" component={SignUpForm} />
          <Route path="*" component={GameStartPage} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
