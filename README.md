# RealtimeTicketingSystem
# Ticketing System

This project is a simple multi-threaded ticketing system that simulates the process of adding and selling tickets. The system allows vendors to add tickets to a pool and customers to purchase them. The system is designed to handle concurrency using Java's `Thread` class and synchronized methods.

## Features

- **Ticket Pool Management**: The system maintains a pool of tickets, where vendors add tickets, and customers buy them.
- **Multiple Vendors and Customers**: The system supports multiple vendors adding tickets and multiple customers purchasing tickets.
- **Configuration**: The system allows users to load or input configurations for ticket management such as the number of tickets, ticket release rate, and customer retrieval rate.
- **Real-Time Process Management**: The system allows users to start and stop the ticket selling process at any time.
- **Persistent Configuration**: Configuration settings are saved and loaded from a JSON file.

## Prerequisites

- Java 11 or higher
- Gson library for JSON processing

You can include the Gson library in your project by adding the following dependency to your `pom.xml` if using Maven:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.8</version>
</dependency>
