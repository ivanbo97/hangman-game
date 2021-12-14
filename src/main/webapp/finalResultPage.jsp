<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/resources/css/landing-page.css"%>
</style>
<title>Your result</title>
</head>
<body>
	<p>Game Result : ${sessionScope.result}</p>
	<form method="post" action="games" class="inline">
		<button type="submit" class="link-button">Start New Game</button>
	</form>
</body>
</html>