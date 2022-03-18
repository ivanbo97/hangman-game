import GameBtn from "./GameBtn";

export default {
  title: "Components/GameBtn",
  component: GameBtn,
};

const Template = (args) => <GameBtn {...args} />;

export const StartGameBtn = Template.bind({});
StartGameBtn.args = {
  size: "large",
  btnTextInit: "New Game",
  btnTextClicked: "Loading game...",
  btnDisabled: false,
};

export const SubmitData = Template.bind({});
SubmitData.args = {
  size: "small",
  btnTextInit: "Submit",
  btnTextClicked: "......",
  btnDisabled: false,
};
