<!DOCTYPE html>
<html>
<head>
<style>

<%@include file="/resources/css/landing-page.css"%>


</style>
<title>${pageTitle}</title>
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<main class = "page-content">
	<div class="player-stat-input">
		<h2 style="color: #ffffff;">${gameResult}</h2>
		${inputFieldForPlayerName}
	</div>
	
	<section class="start-btn-area">
		<form method="post" action="/hangman-game/games" class="inline">
			<button type="submit" id="startGameBtn"
				class="section--start-btn">New Game</button>
		</form>
	</section>
	
	<div class="ranking-table">
	<h3 class="ranking-table-name">${tableName}</h3>
		<br><br>
	<table  class="top-players-table-borderless" >
		<tr class="table-headers">
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
	
	<a class= "new-ranking-url" href=${urlForAdditionalStats}>${urlElementName}</a>
	</main>
</body>
</html>