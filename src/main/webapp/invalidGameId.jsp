<!DOCTYPE html>
<html>
<head>
<style>
body {
	font-family: 'Brush Script MT', cursive;
	text-align: center;
}
</style>
<title>Error Page</title>
</head>

<body>
	<p>Could Not Process The Request. Reason : ${sessionScope.errorMsg}
	</p>
	<form method="post" action="index.jsp" class="inline">
		<button type="submit" class="link-button">Back To Main Page</button>
	</form>
</body>
</html>