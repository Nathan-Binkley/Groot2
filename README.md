# Restaurant Service API

A Spring Boot application that provides REST endpoints for managing restaurant data.

## Features

- Submit restaurant information including:
  - Address details
  - Menu items
  - Reviews
- Retrieve restaurants by zipcode
- Full CRUD operations for restaurants

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (in-memory)
- Gradle

## API Endpoints

### Restaurant Endpoints

- `POST /api/restaurants` - Create a new restaurant
- `GET /api/restaurants` - Retrieve all restaurants
- `GET /api/restaurants/{id}` - Retrieve a restaurant by ID
- `GET /api/restaurants/zipcode/{zipCode}` - Retrieve restaurants by zipcode
- `PUT /api/restaurants/{id}` - Update a restaurant
- `DELETE /api/restaurants/{id}` - Delete a restaurant

## Building and Running

### Prerequisites

- Java 17 or later
- Gradle

### Build and Run

```bash
# Build the application
./gradlew build

# Run the application
./gradlew bootRun
```

The application will be available at http://localhost:8080.

## Database Access

The application uses an H2 in-memory database. 
You can access the H2 console at http://localhost:8080/h2-console with the following credentials:

- JDBC URL: `jdbc:h2:mem:restaurantdb`
- Username: `sa`
- Password: `` (empty)

## Sample Request

```json
POST /api/restaurants
Content-Type: application/json

{
  "name": "Sample Restaurant",
  "description": "A nice family restaurant",
  "phone": "555-123-4567",
  "website": "https://www.samplerestaurant.com",
  "address": {
    "street": "123 Main Street",
    "city": "Anytown",
    "state": "CA",
    "zipCode": "90210"
  },
  "menuItems": [
    {
      "name": "Burger",
      "description": "Delicious cheeseburger",
      "price": 9.99
    },
    {
      "name": "Pizza",
      "description": "Pepperoni pizza",
      "price": 12.99
    }
  ],
  "reviews": [
    {
      "reviewerName": "John Smith",
      "rating": 5,
      "comment": "Excellent food and service!"
    }
  ]
}
``` 