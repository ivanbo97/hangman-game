<!DOCTYPE html>
<html>
<head>
<title>Game Session</title>
<style type="text/css">
body {
  font-family: 'Brush Script MT', cursive;
   text-align: center;
}

h1,h2,p,label{
color:white;
}

.submit-btn{
border-radius: 20%;
width :90px;
}

.submit-btn:hover{
background-color: #4CAF50;
}
</style>
</head>
<body style ="background-color: #2eb8b8;">
<h1>Hangman Game. Current game session details</h1>
<p>Current word : ${gameSessionObj.puzzledWord}</p>
<p>Tries left : ${gameSessionObj.triesLeft}</p>
<p>Number of letters to guess : ${gameSessionObj.lettersToGuessLeft}</p>
<h2>Make your guess</h2>
<form action="${gameSessionObj.gameId}"  method="post">
  <label for="enteredLetter">Enter a letter:</label>
  <input  required type="text" id="enteredLetter" name="enteredLetter"  pattern="[a-z]{1}" style="border-radius: 10px; width: 50px;"
  oninvalid="this.setCustomValidity('Please enter a valid letter. Numbers, special characters or empty strings are not allowed.')"
  oninput="this.setCustomValidity('')"><br><br>
  <input class="submit-btn" type="submit" value="Submit letter!">
</form>
</body>
</html>