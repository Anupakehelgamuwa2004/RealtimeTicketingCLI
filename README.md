# Ticketing System

## Overview
This Ticketing System allows vendors to add tickets to a pool, and customers can purchase tickets from this pool. The system uses multi-threading to simulate multiple vendors adding tickets and customers purchasing tickets concurrently. The configuration is stored in a JSON file and can be loaded on subsequent runs to avoid re-entering the details.

## Prerequisites
Before running the system, ensure you have the following:
- **Java JDK** (version 8 or higher) installed on your system.
- **Google Gson library** for handling JSON operations (you can add it as a dependency in your project).

## Setup
1. **Clone or Download the Project:**
   - Clone this repository or download the source files to your local system.

2. **Compile the Java Files:**
   - If you're using the command line, navigate to the directory where the files are located and compile the Java files using:
     ```
     javac *.java
     ```

3. **Run the Application:**
   - After compiling the files, run the `Main` class:
     ```
     java lk.iit.Main
     ```

## Configuration File
The system uses a configuration file `configuration.json` to store and load the settings for the ticket pool. When the system is first run, it will prompt you to input the following configuration values:
- **Max Ticket Capacity:** The maximum number of tickets that can be in the pool at any given time.
- **Total Tickets:** The total number of tickets that will be managed by the system. This cannot exceed the max ticket capacity.
- **Ticket Release Rate (ms):** The rate (in milliseconds) at which tickets are added to the pool by vendors.
- **Customer Retrieval Rate (ms):** The rate (in milliseconds) at which customers attempt to purchase tickets.

If a configuration file already exists, you will be asked if you want to load the previous configuration. If you choose not to, you will be prompted to enter new values.

## System Operations
Once the configuration is loaded or set, the system will ask you to choose an option:
1. **Start the ticket selling process.**
2. **Stop the process and exit.**

### Ticket Selling Process:
- The system will simulate two vendors adding tickets to the pool and two customers attempting to retrieve tickets concurrently.
- You can stop the process at any time by pressing `2` and the system will gracefully terminate all running threads.

## Key Features
- **Multi-threaded Simulation:** The system uses Java's multi-threading capabilities to simulate the actions of multiple vendors and customers simultaneously.
- **Thread Safety:** The `TicketPool` class ensures thread safety when adding and retrieving tickets.
- **Configuration Persistence:** Configuration settings are saved in a `configuration.json` file and can be reloaded for future runs.

## Stopping the Process
- While the system is running, you can press `2` at any time to stop the ticket selling process and exit the program.
- The program will log information about the stopping process and ensure all threads are properly terminated.

## Example Usage:
- When prompted, input valid configuration values for:
  - Max Ticket Capacity
  - Total Tickets
  - Ticket Release Rate (milliseconds)
  - Customer Retrieval Rate (milliseconds)

- The system will then prompt you to either start the ticket selling process or stop it.
- Press `1` to start, and you will see logs in the terminal as vendors add tickets and customers retrieve them.
- Press `2` to stop the process.

## Logging
The system uses `java.util.logging` to log key events. Logs are displayed in the console and include information such as:
- Vendor actions (adding tickets).
- Customer actions (purchasing tickets).
- Errors or interruptions in the process.

## Troubleshooting
- **Invalid Input:** If you enter an invalid input during configuration, the system will prompt you to enter a valid value.
- **File Not Found:** If the configuration file is missing, the system will prompt you to provide the necessary input.
- **Thread Interruptions:** If the system is interrupted (e.g., by stopping the process), appropriate messages will be logged.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

