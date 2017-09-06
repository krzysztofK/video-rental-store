I. Made decisions
 
 Technical: 
 1. Maven will be used.
 2. Suggested framework - dropwizard will be used
 3. HSQLDB will be used as database for simple local application setup
 
 Functional
 1. Application will enable to add a film to the database
 2. Application will enable to add customer to the database
 3. Application will enable to create rental for specific customer with specific films with some rental start date
 4. Application will calculate rental price and its bonus points
 5. Application gives bonus points to the customer
 6. Application will enable to return specific films from rental
 7. Application will calculate surcharges if applicable
 
II. Running
 
 1. To build execute command
  mvn clean install
 2. To run execute
  java -jar target/video-rental-store-1.0.0-SNAPSHOT.jar server src/main/resources/local.yml
  
III. Usage

  1. To add customer send
  
POST /customers HTTP/1.1
Host: localhost:8090
Content-Type: application/json

{
	"name": "krzysztof",
	"surname": "K"
}  

The response will be 

{
    "id": 2,
    "name": "krzysztof",
    "surname": "K",
    "bonusPoints": 0
}
  2. To add a film send 

POST /films HTTP/1.1
Host: localhost:8090
Content-Type: application/json

{
	"title": "The Godfather",
	"type": "OLD_FILM"
}

The response will be

{
    "id": 1,
    "title": "The Godfather",
    "type": "OLD_FILM"
}

  3. To rent a film send

POST /rentals/ HTTP/1.1
Host: localhost:8090
Content-Type: application/json

{
	"rentalDate":"2017-09-04T22:05:26+00:00",
	"films": [
		{
			"filmId":1,
			"rentedForDays":2
		}
	],
	"customerId":1
}
The response will be

{
    "id": 1,
    "rentalDate": "2017-09-04T22:05:26Z",
    "films": [
        {
            "film": {
                "id": 1,
                "title": "The Godfather",
                "type": "OLD_FILM"
            },
            "price": "SEK 30.00",
            "bonusPoints": 1
        }
    ],
    "totalPrice": "SEK 30.00",
    "totalBonusPoints": 1
}

  4. To check bonus points customer status send

GET /customers/1 HTTP/1.1
Host: localhost:8090

The response will be

{
    "id": 1,
    "name": "krzysztof",
    "surname": "K",
    "bonusPoints": 1
}

  5. To return the film send
  
POST /rentals/1/return HTTP/1.1
Host: localhost:8090
Content-Type: application/json

{	
	"returnDate": "2017-09-10T22:02:26+00:00",
	"films" : [1]
}

The response will be:

{
    "surcharges": [
        {
            "film": {
                "id": 1,
                "title": "The Godfather",
                "type": "OLD_FILM"
            },
            "extraDays": 4,
            "surcharge": "SEK 30.00"
        }
    ],
    "totalLateCharge": "SEK 30.00"
}