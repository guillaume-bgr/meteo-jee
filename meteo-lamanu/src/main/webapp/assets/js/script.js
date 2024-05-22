document.addEventListener("DOMContentLoaded", function() {
	
	// As the API sends 6 records per day, to regroup the data we take six statements a day
	let countByDay = 8;
	let timer;
	
	if (document.querySelectorAll('.city.records').length > 0 ) {
		for (let city of document.getElementsByClassName('city')) {
		    var latitude = city.dataset.lat;
	        var longitude = city.dataset.lng;
	        var key = '651ed0f5b1a53ce77d3aec9b6ce224e7';
		    fetch('https://api.openweathermap.org/data/2.5/forecast?lat='+latitude+'&lon='+longitude+'&appid='+key+'&lang=fr&units=metric')
			  .then(function(response) {
			    return response.json();
			  })
			  .then(function(myJson) {
				console.log(myJson);
				displayThumbnail(city, myJson);
				displayForecast(city, myJson);
			  });
		}
	} else {
		navigator.geolocation.getCurrentPosition(function(position) {
	        // Extract latitude and longitude from the position object
	        var latitude = position.coords.latitude;
	        var longitude = position.coords.longitude;
	        var key = '651ed0f5b1a53ce77d3aec9b6ce224e7';
	        fetch('https://api.openweathermap.org/data/2.5/forecast?lat='+latitude+'&lon='+longitude+'&appid='+key+'&lang=fr&units=metric')
			  .then(function(response) {
			    return response.json();
			  })
			  .then(function(myJson) {
				console.log(myJson);
				displayThumbnail(document.querySelector('.city'), myJson);
				displayForecast(document.querySelector('.city'), myJson);
			  });
	        // Do something with the latitude and longitude
	        console.log("Latitude: " + latitude + ", Longitude: " + longitude);
	    }, function(error) {
	        // Handle errors
	        switch(error.code) {
	            case error.PERMISSION_DENIED:
	                console.error("User denied the request for Geolocation.");
	                break;
	            case error.POSITION_UNAVAILABLE:
	                console.error("Location information is unavailable.");
	                break;
	            case error.TIMEOUT:
	                console.error("The request to get user location timed out.");
	                break;
	            case error.UNKNOWN_ERROR:
	                console.error("An unknown error occurred.");
	                break;
	        }
	    });
	}
	
    
	document.querySelector('.searchbar-container .searchbar').addEventListener('keyup', function() {
		clearTimeout(timer);
		let value = this.value;	
		
		timer = setTimeout(() => search(value), 500);
	});
	
	document.querySelector('.city-container').addEventListener('click', function(e) {
		document.querySelector('.search-results').style.display = "none";
	})
	
	document.addEventListener("click", function(e){
		const target = e.target.closest(".result-city"); // Or any other selector.
	
		if(target){
			let form = document.querySelector('.results-form');
			fetch('http://127.0.0.1:8080/meteo-lamanu/getlatlng?place_id='+target.dataset.placeId)
			.then(function(response) {
	    		return response.json();
  			}).then(function(json) {
	    		form.querySelector('input[name=name]').value = target.dataset.city;
	    		form.querySelector('input[name=place_id]').value = target.dataset.placeId;
	    		form.querySelector('input[name=lat]').value = json.result.geometry.location.lat;
	    		form.querySelector('input[name=lng]').value = json.result.geometry.location.lng;
	    		document.resultsForm.submit();
			})
  		}
	});
	
	// I know it is useless to call an internal route that itself calls an API, but I had to do it for the sake of the project
	function search(value) {
		fetch('http://127.0.0.1:8080/meteo-lamanu/search?value='+value)
		.then(function(response) {
	    	return response.json();
	  	}).then(function(json) {
			document.querySelector('.search-results').style.display = 'block';
			document.querySelector('.search-results').innerHTML = '';
			console.log(json);
			json.predictions.forEach(function(prediction) {
				document.querySelector('.search-results').insertAdjacentHTML('beforeend' ,`
					<div class="result-city" data-city="${prediction.structured_formatting.main_text}" data-place-id="${prediction.place_id}">
						<div>
							<span class="city-name">${prediction.structured_formatting.main_text}</span>
							<span class="city-subtitle">${prediction.structured_formatting.secondary_text}</span>
						</div>
						<span>+</span>
					</div>
				`);
			})
		})
	}
    
    // Because the only API free is 5 day / 3 hour forecast, I cannot recover passed statements.
    // Therefore I cannot recover previous temperatures for the day. This function exists in order to not recover
    // data for tomorrow at noon if we make our query today at noon, for example
    function getStmtsLeft(json) {	
		let lastStmt = json.list[0].dt_txt;	
		let stmtsLefts = countByDay - new Date(lastStmt).getHours() / 3;
		
		return stmtsLefts;
	}
	
	function displayThumbnail(city, json) {
		city.querySelector('.city-title .name').textContent= json.city.name + ', ';
		city.querySelector('.city-title .country').textContent= json.city.country;
	   	city.querySelector('img').src= './assets/img/'+json.list[0].weather[0].main.toLowerCase()+'.png';
	   	city.querySelector('.weather-text').textContent = json.list[0].weather[0].description;
	   	city.querySelector('.weather-temp .min').textContent = Math.round(getMinTemp(json.list.slice(0, getStmtsLeft(json)))) + '°';
		city.querySelector('.weather-temp .max').textContent = Math.round(getMaxTemp(json.list.slice(0, getStmtsLeft(json)))) + '°';
	}
    
    function displayForecast(city, json) {
		// As the API sends 6 records per day, to regroup the data we take six statements a day
		for (i = 0; i < 4; i++) {
			let dayForecast;
			if (i == 0) {
				dayForecast = json.list.slice(i, getStmtsLeft(json));
			} else {
				dayForecast = json.list.slice(i * countByDay - getStmtsLeft(json) , i * countByDay + countByDay);
			}
			let stmtDate = new Date(dayForecast[i].dt_txt);
			city.querySelectorAll('.forecast-item .forecast-date')[i].textContent = ("0" + stmtDate.getDate()).slice(-2)+'/'+("0" + stmtDate.getMonth()).slice(-2);
			city.querySelectorAll('.forecast-item img')[i].src = './assets/img/'+getDayWeather(dayForecast).toLowerCase()+'.png';
			city.querySelectorAll('.forecast-item .forecast-min')[i].textContent = Math.round(getMinTemp(dayForecast)) + '°';
			city.querySelectorAll('.forecast-item .forecast-max')[i].textContent = Math.round(getMaxTemp(dayForecast)) + '°';
			city.querySelectorAll('.forecast-item .forecast-wind')[i].textContent = getAvgWind(dayForecast) + ' m/s';
		} 
	}
    
    function getMinTemp(json) {
		min = null;
		
		json.forEach(function(data) {
			if (data.main.temp < min || min == null) {
				min = data.main.temp
			}
		})
		
		return min;
	}
	
	function getMaxTemp(json) {
		max = null;
		
		json.forEach(function(data) {
			if (data.main.temp > max || max == null) {
				max = data.main.temp
			}
		})
		
		return max;
	}
	
	function getAvgWind(json) {
		windValues = [];
		
		json.forEach(function(data) {
			windValues.push(data.wind.speed);
		})
		
		let sum = windValues.reduce((partialSum, a) => partialSum + a, 0);
		
		return Math.round(sum / windValues.length * 100) / 100;
	}
	
	function getDayWeather(json) {
		weathers = [];

		json.forEach(function(data) {
			weathers.push(data.weather[0].main);
		})
		
	    const counts = {};
	    let maxCount = 0;
	    let mostFrequent = null;
	
	    weathers.forEach(string => {
	        counts[string] = (counts[string] || 0) + 1;
	        if (counts[string] > maxCount) {
	            maxCount = counts[string];
	            mostFrequent = string;
	        }
	    });
	    
	    return mostFrequent;
	}
});