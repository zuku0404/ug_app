# Application Overview

This Java application is designed to convert the cost of computers purchased in USD to PLN based on the exchange rate provided by the National Bank of Poland (NBP) REST API for a specific date. The result is saved in an XML file and in a database. The application also allows querying and displaying data from the saved database in a user-friendly format.

## Installation

WITHOUT DOCKER:
* BACKEND APPLICATION:
    1. Clone the repository:
        ```bash
        git clone https://github.com/zuku0404/ug_app.git
        ```
    2. Navigate to the project directory:
        ```bash
        cd ug_app/currency/
        ```
    3. Build the project using Maven or Gradle:
        ```bash
        ./gradlew build
        ```
    4. Run the application:
        ```bash
        ./gradlew bootRun
        ```

* FRONTEND  APPLICATION:
    1. Install Node.js 
    2. Navigate to the project directory:
        ```bash
        cd ug_app/frontend/
        ```
    3. Install a simple HTTP server
        ```bash
        npm install -g http-server
        ```
    4. Run the server:
        ```bash
        http-server
        ```

WITH DOCKER:
* APP:
    1. Clone the repository:
        ```bash
        git clone https://github.com/zuku0404/ug_app.git
        ```
    2. Navigate to the project directory:
        ```bash
        cd ug_app/
        ```
    3. Run docker compose file:
        ```bash
        docker-compose -f docker-compose.yml up --build
        ```

## API Endpoints

### Get Orders

- **URL:** `/api/orders`
- **Method:** `GET`
- **Request Parameters:**
    - `productName (Optional, String):`
        * Filters orders by the product name. The filtering is case-insensitive, and you can provide a partial product name to match multiple orders.
    - `postDate (Optional, String in ISO date format yyyy-MM-dd):`
        * Filters orders by the post date. You can specify a specific date to retrieve orders posted on that day.
    - `sortField (Optional, String):`
        * Specifies the field by which the results should be sorted. The available fields are ID (default), productName, postDate
    - `sortDirection (Optional, String):`
        * Specifies the sorting direction. Can either be ASC (ascending) or DESC (descending). The default value is ASC.
- **Headers:**
    - `Accept: application/json`
      - **Response:**
          - `200 OK`
            * Example:
              ```json
               [
                    {
                        "id": 1,
                        "productName": "ACER Aspire",
                        "postDate": "2025-01-03",
                        "costUsd": 345.00,
                        "costPln": 1432.16
                    },
                    {
                        "id": 2,
                        "productName": "DELL Latitude",
                        "postDate": "2025-01-10",
                        "costUsd": 543.00,
                        "costPln": 2248.83
                    },
                    {
                        "id": 3,
                        "productName": "HP Victus",
                        "postDate": "2025-01-19",
                        "costUsd": 346.00,
                        "costPln": null
                    }
                ]
              ```
          - `500 Internal Server Error`
            * Example:
              ```json
              {
               "status": 500,
               "message": "something wrong",
               "date": "2025-10-03"
              }
              ```
## Usage

Once the application is running, you can access the API at:
* Without Docker: http://127.0.0.1:8081/home-page.html
* With Docker: http://127.0.0.1:5500/home-page.html

The frontend allows users to search for orders by product name; however, the search is case-sensitive. Additionally, users can filter orders by posting date. Sorting is available by product name and posting date.
Please note: if the user chooses to filter by date, the sorting option by date becomes hidden and unavailable to avoid conflicting filters.

If you run application by docker you can find file faktura.xml which is automatically generate when you start application to use commands 
        ```bash
            docker exec -it spring-backend /bin/bash
        ```
        ```bash
            cat faktura.xml
        ```
Without docker you can find this file into currency folder.

## Built With

- **[Java 21](https://openjdk.org/projects/jdk/21/)** - The programming language.
- **[Spring Boot 3](https://spring.io/projects/spring-boot)** - A framework to development of Java applications.
- **[Gradle](https://gradle.org/)** - Dependency Management
- **[Jackson](https://github.com/FasterXML/jackson)** - A library used for converting Java objects to JSON and vice versa.
- **[Lombok](https://projectlombok.org/)** - A library that helps to reduce boilerplate code by providing annotations to auto-generate getters, setters, constructors, etc.

## Authors
* Mateusz Żuk - Initial work

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.
