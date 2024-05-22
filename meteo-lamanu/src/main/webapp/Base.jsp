<%@ page import="java.util.ArrayList"%>
<%@ page import="com.meteo.models.UserCity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
				<div class="searchbar-container">
					<input class="searchbar" type="text" placeholder="Ajouter une ville..">	
					<span class="add-sign">+</span>
				</div>
				<div class="header-login">
					<%
			            boolean isConnected = (boolean) request.getAttribute("isConnected");
			            if (isConnected == true) {
			        %>
			        <a href="logout" class="login-icon">
						<svg style="width: 100%; height: 100%" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
						<rect opacity="0.3" width="12" height="2" rx="1" transform="matrix(-1 0 0 1 15.5 11)" fill="currentColor"/>
						<path d="M13.6313 11.6927L11.8756 10.2297C11.4054 9.83785 11.3732 9.12683 11.806 8.69401C12.1957 8.3043 12.8216 8.28591 13.2336 8.65206L16.1592 11.2526C16.6067 11.6504 16.6067 12.3496 16.1592 12.7474L13.2336 15.3479C12.8216 15.7141 12.1957 15.6957 11.806 15.306C11.3732 14.8732 11.4054 14.1621 11.8756 13.7703L13.6313 12.3073C13.8232 12.1474 13.8232 11.8526 13.6313 11.6927Z" fill="currentColor"/>
						<path d="M8 5V6C8 6.55228 8.44772 7 9 7C9.55228 7 10 6.55228 10 6C10 5.44772 10.4477 5 11 5H18C18.5523 5 19 5.44772 19 6V18C19 18.5523 18.5523 19 18 19H11C10.4477 19 10 18.5523 10 18C10 17.4477 9.55228 17 9 17C8.44772 17 8 17.4477 8 18V19C8 20.1046 8.89543 21 10 21H19C20.1046 21 21 20.1046 21 19V5C21 3.89543 20.1046 3 19 3H10C8.89543 3 8 3.89543 8 5Z" fill="currentColor"/>
						</svg>
					</a>
					<%
			            } else {
			        %>
					<a href="login" class="login-icon">
						<svg viewBox="0 0 18 18" fill="none" xmlns="http://www.w3.org/2000/svg">
						<path opacity="0.3" d="M16.5 9C16.5 13.125 13.125 16.5 9 16.5C4.875 16.5 1.5 13.125 1.5 9C1.5 4.875 4.875 1.5 9 1.5C13.125 1.5 16.5 4.875 16.5 9Z" fill="currentColor"/>
						<path d="M9 16.5C10.95 16.5 12.75 15.75 14.025 14.55C13.425 12.675 11.4 11.25 9 11.25C6.6 11.25 4.57499 12.675 3.97499 14.55C5.24999 15.75 7.05 16.5 9 16.5Z" fill="currentColor"/>
						<rect x="7" y="6" width="4" height="4" rx="2" fill="currentColor"/>
						</svg>
					</a>
					<%
			            }
			        %>
				</div>
			</div>
			<div class="header-border">
			</div>
		</header>
		<main>
			<div class="city-container default">
				<%
		            ArrayList<UserCity> userCities = (ArrayList<UserCity>) request.getAttribute("userCities");
		            if (userCities != null) {
		                for (UserCity userCity : userCities) {
		        %>
		                	<div class="city records" data-place-id="<%= userCity.getPlaceId() %>" data-lat="<%= userCity.getLat() %>" data-lng="<%= userCity.getLng() %>">
								<div class="title-container">
									<h1 class="city-title"><span class="name"></span><span class="country"></span></h1>
									<a class="delete" href="./remove-city?place_id=<%= userCity.getPlaceId() %>">Supprimer</a>
								</div>
								<div class="weather-thumbnail">
									<div>
										<span class="weather-text"></span>
										<span class="weather-temp"><span class="min"></span> / <span class="max"></span></span> 
									</div>
									<img >
								</div>
								<h2>Relevés sur quatre jours</h2>
								<div class="forecast-5days">
									<div class="forecast-item today">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
								</div>
							</div>
				<%
		                }
		            } else {
		            	%>
		            		<div class="city norecords">
								<div class="title-container">
									<h1 class="city-title"><span class="name"></span><span class="country"></span></h1>
								</div>
								<div class="weather-thumbnail">
									<div>
										<span class="weather-text"></span>
										<span class="weather-temp"><span class="min"></span> / <span class="max"></span></span> 
									</div>
									<img >
								</div>
								<h2>Relevés sur quatre jours</h2>
								<div class="forecast-5days">
									<div class="forecast-item today">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
									<div class="forecast-item">
										<span class="forecast-date"></span>
										<img>
										<span class="forecast-max"></span>
										<span class="forecast-min"></span>
										<span class="forecast-wind"></span>
									</div>
								</div>
							</div>
		            	<%
		            }
		        %>
			</div>
			<form method="POST" class="results-form" name="resultsForm" action="./add-city">
				<input type="hidden" name="name" value="">
				<input type="hidden" name="place_id" value="">
				<input type="hidden" name="lat" value="">
				<input type="hidden" name="lng" value="">
				<div class="search-results">
				</div>
			</form>
		</main>
		<script src="./assets/js/script.js" charset="utf-8"></script>
	</body>
</html>