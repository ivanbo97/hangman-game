<!DOCTYPE html>
<html>
<head>
<title>Game Session</title>
<style>
<%@include file="/resources/css/hangmanMainPage.css"%>
</style>
</head>
<body style="background-color: #2eb8b8;">
	<h1>Hangman Game. Current game session details</h1>

	<div>
		<div style="display: inline-block">
			<p>Current word:</p>
		</div>
		<p style="display: inline-block" id="puzzled-word">${gameSessionObj.puzzledWord}</p>
	</div>

	<div>
		<div style="display: inline-block">
			<p>Tries Left:</p>
		</div>
		<p style="display: inline-block" id="tries-left">${gameSessionObj.triesLeft}</p>
	</div>

	<div>
		<div style="display: inline-block">
			<p>Number of letters to guess :</p>
		</div>
		<p style="display: inline-block" id="letters-to-guess">${gameSessionObj.lettersToGuessLeft}</p>
	</div>
	<div class="guess-area">
	<h2>Make your guess</h2>
	<form action="${gameSessionObj.gameId}" method="post">
		<label for="enteredLetter">Enter a letter:</label> <input required
			type="text" id="enteredLetter" name="enteredLetter"
			pattern="[a-z]{1}" style="width: 50px;"
			oninvalid="this.setCustomValidity('Please enter a valid letter. Numbers, special characters or empty strings are not allowed.')"
			oninput="this.setCustomValidity('')"><br>
		<br> <input id="submit-letter-btn" class="submit-btn"
			type="submit" value="Submit letter!">
	</form>
	</div>
	<!-- Invisible to user, for test purpose only -->
	<form style="visibility: hidden" method="post" action="games"
		class="inline">
		<button type="submit" id="startGameBtn">Start New Game</button>
	</form>
	<p id="secret-letters" class="maskWithBackground">${gameSessionObj.lettersToBeGuessedEncoded}</p>
</body>
</html>