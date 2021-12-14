<!DOCTYPE html>
<html>
<head>
<title>Game Session</title>
</head>
<body>
<h1>Hangman Game. Current game session details</h1>
<p>Current word : ${requestScope.gameSessionObj.puzzledWord}</p>
<p>Tries left : ${requestScope.gameSessionObj.triesLeft}</p>
<p>Number of letters to guess : ${requestScope.gameSessionObj.lettersToGuessLeft}</p>
<h2>Make your guess</h2>
<form action="${requestScope.gameSessionObj.gameId}"  method="post">
  <label for="enteredLetter">Enter a letter:</label>
  <input  required type="text" id="enteredLetter" name="enteredLetter"  pattern="[a-z]{1}"><br><br>
  <input type="submit" value="Submit your guess!">
</form>
</body>
</html>