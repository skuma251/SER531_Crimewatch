# CrimeWatch Application

This application provides a comprehensive platform to manage crime data, offering APIs for data retrieval and a web interface for visualization.

## Prerequisites

Ensure you have the following software installed on your system:

- [Apache Fuseki](https://jena.apache.org/download/)
- [Java 17](https://www.oracle.com/java/technologies/downloads/)
- [Node.js](https://nodejs.org/en/download)

## Installation and Setup

### 1. Setup Fuseki

- Download Fuseki from [https://jena.apache.org/download/] and install it.
- Run Fuseki server `fuseki-server`.
- Go to the url provided by fuseki `http://localhost:3030`.
- Create 2 datasets named as `Complaints_Data` and `Shooting_HateCrime_Data` datasets and select the persistent option.
- Go to rdf_triples folder under SER531_Crimewatch project folder and download all the 3 zip files.
- After extracting the zip files, upload the 2 files(complaints_data.rdf and complaints_data_part2.rdf) in the `Complaints_Data dataset` and other two files (hatecrime_data.rdf and shooting_data.rdf) in the `Shooting_HateCrime_data` dataset.

### 2. Setup and Run Spring Boot

- Clone this repository.
- Navigate to the `crimewatch` folder.
- For Mac, run `./gradlew bootRun`. For Windows, run `gradlew.bat bootRun`.

### 3. Test the API with Postman (optional step)

- Open Postman and import the Postman collection present in the root of the project.
- Test the API requests to ensure they are responding correctly.

### 4. Run the Web App

- Navigate to the `crimewatch_webapp` folder.
- Install the Node dependencies by running `npm install` or `npm i`.
- Start the development server using `npm run dev`.

The frontend app will be available at the provided URL.

## Additional Notes

- Make sure all necessary ports (e.g., Fuseki, Spring Boot server) are not blocked by your firewall.
- Ensure proper configurations are set up in the application for database connections, API endpoints, etc.
