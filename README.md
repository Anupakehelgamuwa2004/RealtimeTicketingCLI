# Ticket Selling System User Guide

## Overview
This is a multi-threaded ticket selling system that allows vendors to add tickets to a pool and customers to retrieve tickets concurrently. The system provides a configurable approach to managing ticket sales with concurrent processing.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Gson library (for JSON configuration)

## System Components
The system consists of four main classes:
- `Main`: The entry point of the application
- `Configuration`: Manages system configuration settings
- `TicketPool`: Handles ticket addition and retrieval
- `Vendor`: Adds tickets to the pool
- `Customer`: Retrieves tickets from the pool

## Configuration Options
When you start the application, you'll be prompted to configure the following parameters:

1. **Max Ticket Capacity**: The maximum number of tickets that can be in the system
2. **Total Tickets**: The total number of tickets to be managed
3. **Ticket Release Rate**: Time (in milliseconds) between ticket additions by vendors
4. **Customer Retrieval Rate**: Time (in milliseconds) between ticket retrievals by customers

### Important Configuration Rules
- Total Tickets cannot exceed Max Ticket Capacity
- All inputs must be positive integers
- Configuration can be saved and loaded between sessions

## Running the Application

### Starting the Application
1. Compile the Java files
2. Run the `Main` class

### Initial Configuration
When first run, you'll see two scenarios:
- **No Previous Configuration**: You'll be prompted to enter all configuration parameters
- **Existing Configuration**: You can choose to load previous settings or create new ones

### System Operation
After configuration, you have two options:
1. **Start Ticket Selling Process**: 
   - Two vendors will add tickets
   - Two customers will purchase tickets
   - System runs concurrently

2. **Stop Process**: Exit the application

### During Ticket Selling
- Press `2` at any time to stop the ticket selling process
- The system will log activities to the console and a log file

## Configuration File
- A `configuration.json` file is created to save your settings
- You can modify this file manually or through the application

## Logging
- System uses Java's built-in logging
- Logs include timestamps, log levels, and descriptive messages
- Helpful for tracking system activities and debugging

## Concurrency Model
- Uses Java threads to simulate concurrent ticket selling
- Synchronized methods ensure thread-safe ticket pool operations
- Vendors can add tickets
- Customers can retrieve tickets
- System stops when all tickets are sold or manually interrupted

## Troubleshooting
- Ensure positive integer inputs
- Check console for any error messages
- Verify Gson library is in your classpath

## Example Workflow
1. Start application
2. Configure system (e.g., 100 max capacity, 80 total tickets)
3. Choose to start ticket selling
4. Watch vendors add tickets
5. Observe customers purchasing tickets
6. Stop process when desired

## Limitations
- Fixed to two vendors and two customers
- No persistence of ticket sales across application restarts

## Dependencies
- Requires Gson library for JSON configuration management

## License
[Add your license information here]

## Contributors
[List contributors or your name]
