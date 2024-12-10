package lk.iit;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        configureLogger();
        Configuration config = new Configuration();
        Logger LOGGER = Logger.getLogger(Main.class.getName());
        Scanner scanner = new Scanner(System.in);

        File configFile = new File(Configuration.CONFIGURATION_FILE);
        if (configFile.exists()) {
            System.out.print("Previous configuration found. Do you want to load it? (yes/no): ");
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("yes")) {
                config.loadConfiguration();
                System.out.println("Previous configuration loaded.");
            } else {
                config.promptUserInput(scanner);
            }
        } else {
            System.out.println("No previous configuration found.");
            config.promptUserInput(scanner);
        }

        // Prompt the user to select an option to start or stop the ticket selling process
        System.out.println("\nSelect an option:");
        System.out.println("1. Start the ticket selling process.");
        System.out.println("2. Stop the process and exit.");
        System.out.print("Enter your choice (1/2): ");

        int option = -1;
        if (scanner.hasNextInt()) {
            option = scanner.nextInt();
            scanner.nextLine();
        } else {
            String invalidInput = scanner.nextLine();
            System.out.println("Invalid input: " + invalidInput);
        }

        if (option == 1) {
            TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

            Thread vendor1Thread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), "Vendor 1"));
            Thread vendor2Thread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), "Vendor 2"));
            Thread customer1Thread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer 1"));
            Thread customer2Thread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer 2"));

            vendor1Thread.start();
            vendor2Thread.start();
            customer1Thread.start();
            customer2Thread.start();

            Thread inputThread = new Thread(() -> {
                while (true) {
                    System.out.println("\nPress 2 to stop the process at any time.");
                    if (scanner.hasNextInt()) {
                        int userInput = scanner.nextInt();
                        scanner.nextLine();
                        if (userInput == 2) {
                            System.out.println("Stopping the ticket selling process...");
                            LOGGER.log(Level.INFO, "User selected to stop the process.");
                            vendor1Thread.interrupt();
                            vendor2Thread.interrupt();
                            customer1Thread.interrupt();
                            customer2Thread.interrupt();
                            break;
                        } else {
                            System.out.println("Invalid input. Please press 2 to stop the process.");
                        }
                    } else {
                        String invalidInput = scanner.nextLine();
                        System.out.println("Invalid input: " + invalidInput);
                    }
                }
            });

            inputThread.start();

            try {
                vendor1Thread.join();
                vendor2Thread.join();
                customer1Thread.join();
                customer2Thread.join();
                inputThread.join();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "The process was interrupted.");
            }

            LOGGER.log(Level.WARNING, "Ticketing system has ended.");
        } else if (option == 2) {
            System.out.println("Process stopped. Exiting program...");
            LOGGER.log(Level.INFO, "Program exited by the user.");
            System.exit(0);
        } else {
            System.out.println("Invalid option. Exiting program...");
            LOGGER.log(Level.WARNING, "Invalid option selected. Program exited.");
            System.exit(0);
        }
        // Close the scanner at the very end if needed (though not required as program exits)
        // scanner.close(); // Optional: Not necessary since the program will exit
    }

    /**
     * Configures the logger to use a custom format for logging messages.
     * The log format will include the timestamp, log level, and message.
     */
    private static void configureLogger() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$s] %5$s %n");
        Logger logger = Logger.getLogger("");
        logger.getHandlers()[0].setLevel(Level.ALL);
    }
}
