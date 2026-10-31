# WarrantyHub Digital Warranty and Product Tracker – Backend

- **Student:** Kambogo Mugabo Bonheur
- **Student ID:** 27788
- **Course:** INSY 8322 – Mid-term project
- **Lecturer:** Patrick Dushimimana
- **Project name:** warranty-hub-digital-warrantyand-product-tracker

## Requirements

- Java 21
- Maven
- PostgreSQL
- Postman (to test the API)

Built with Spring Boot 4.0.9, Spring Web, Spring Data JPA and the PostgreSQL driver.

## How to run the API

1. Create the database in PostgreSQL:

   ```sql
   CREATE DATABASE warranty_hub;
   ```

2. Check the database settings in `src/main/resources/application.properties`.
   Change the username and password if yours are different:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/warranty_hub
   spring.datasource.username=postgres
   spring.datasource.password=1234
   ```

3. From the project folder, start the app:

   ```
   mvn spring-boot:run
   ```

4. The API runs on `http://localhost:8080`. The tables are created automatically (`ddl-auto=update`).

## Testing with Postman

1. Open Postman → **Import** → choose `warranty-hub-digital-warrantyand-product-tracker-midterm.postman_collection.json` from this repository.
2. The collection has one request per requirement, named by its ID (A1, A2, B1 ... F2, Bonus 1, Bonus 2).
3. A new database starts empty. Enter the seed data first through the `/save` endpoints. The ids in the saved requests come from my own database, so replace them with your own ids.
