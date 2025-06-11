This README explains what the server does, how to run it, and what API endpoint it exposes.

```markdown
# LogSense Server

A Spring Boot application designed to receive, process, and store log events from the `log-sense-logback-appender`.

## About The Project

This server acts as a centralized collector for logs sent from various microservices. It exposes a simple REST API endpoint that accepts structured JSON log events and makes them available for storage and future analysis. It is the designated backend for the `log-sense-logback-appender`.

## Features

-   Listens for structured JSON log events via a REST API.
-   Built with Spring Boot 3 and Java 21.
-   Designed for high-throughput log ingestion.
-   (Future) Can be extended to persist logs to a database like Elasticsearch, MongoDB, or a relational database.
-   (Future) Can provide a query API or a simple UI to view logs.

## Prerequisites

-   Java 21 or later
-   Maven 3.8+

## Getting Started

To get a local copy up and running, follow these simple steps.

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd logsense-server
2. Build the Project
Build the application using Maven. This will download dependencies and package the application as an executable JAR file.

Bash

mvn clean install
3. Run the Server
You can run the application from the command line:

Bash

java -jar target/logsense-server-*.jar
By default, the server will start on port 8080.

Configuration
You can configure the application server port and other settings in src/main/resources/application.properties.

Properties

# The port the server will run on
server.port=8080
API Endpoints
The server exposes one primary endpoint for receiving logs.

Receive Log Events
URL: /api/logs

Method: POST

Description: Accepts a JSON object representing a single log event.

Success Response: 202 ACCEPTED - The log has been accepted for processing.

Request Body (JSON):

The server expects a JSON payload with the following structure:

JSON

{
  "message": "User successfully authenticated.",
  "serviceName": "authentication-service",
  "logLevel": "INFO",
  "timestamp": 1678886400000,
  "threadName": "http-nio-8081-exec-5",
  "loggerName": "com.example.auth.AuthenticationService",
  "mdc": {
    "userId": "user-123",
    "requestId": "req-abc-789"
  },
  "stackTrace": null
}
License
Distributed under the MIT License. See LICENSE for more information.