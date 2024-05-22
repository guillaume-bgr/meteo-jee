<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Dashboard</title>
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="./assets/css/style.css">
	</head>
	<body>
		<header>
			<div class="header-content d-flex">
				<h1>S'inscrire</h1>
			</div>
			<div class="header-border">
			</div>
		</header>
		<main>
			<div class="login">
				<form method="POST">
					<input type="text" name="email" placeholder="Email" value="guillaumebongrand1@gmail.com">
					<input type="password" name="password" placeholder="Mot de passe" value="password">
					<button>S'inscrire</button>
				</form>
			</div>
		</main>
		<script src="./assets/js/script.js"></script>
	</body>
</html>