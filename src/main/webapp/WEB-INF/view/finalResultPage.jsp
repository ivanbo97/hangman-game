<!DOCTYPE html>
<html>
<head>
<style>
body {
  font-family: 'Brush Script MT', cursive;
   text-align: center;
}
</style>
<title>Your result</title>
</head>
<body>
	<h1>Game Result : </h1>
	<h2>${gameResult}</h2>
	<form method="post" action="/hangman-game/games" class="inline">
		<button type="submit" class="link-button">Start New Game</button>
	</form>
</body>
</html>