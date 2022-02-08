<!DOCTYPE html>
<html>
<head>
<style>

<%@include file="/resources/css/landing-page.css"%>

body {
  font-family: 'Brush Script MT', cursive;
   text-align: center;
}
</style>
<title>${pageTitle}</title>
</head>
<body style="background-color: #2eb8b8;">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div>
		<h2 style="color: #ffffff;">${gameResult}</h2>
		<div>${inputFieldForPlayerName}</div>
	</div>
	<form method="post" action="/hangman-game/games" class="inline">
		<button type="submit" id="startGameBtn"
			class="link-button">Start New Game</button>
	</form>
	
	<div id="ranking-table-top-10">
	<h3>${tableName}</h3>
		<br><br>
	<table id="customers" class="center" border="1" width="90%">
		<tr>
			<th>Player Name</th>
			<th>Total Wins</th>
		</tr>
		<c:forEach items="${topPlayers}" var="player">
			<tr>
				<td>${player.playerName}</td>
				<td>${player.totalWins}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<a href=${urlForAdditionalStats}>${urlElementName}</a>
</body>
</html>