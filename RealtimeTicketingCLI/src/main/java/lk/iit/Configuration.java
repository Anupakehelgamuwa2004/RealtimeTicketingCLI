package lk.iit;

import com.google.gson.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private int maxTicketCapacity;
    public static int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    public static final String CONFIGURATION_FILE = "configuration.json";

    /**
     * Prompts the user for input values such as max ticket capacity, total tickets,
     * ticket release rate, and customer retrieval rate. This method ensures that
     * input values are valid (positive integers) and saves the configuration after
     * the input is gathered.
     *
     * @param scanner the Scanner instance used for reading user input
     */
    public void promptUserInput(Scanner scanner) {
        try {
            maxTicketCapacity = validateInput("Enter Max Ticket Capacity", scanner);
            totalTickets = validateInput("Enter Total Tickets", scanner);
            if (totalTickets > maxTicketCapacity) {
                System.out.println("Total Tickets cannot exceed Max Ticket Capacity. \nPlease try again with a less than or equal number to Max Ticket Capacity.");
                totalTickets = validateInput("Total Tickets", scanner);
            }
            ticketReleaseRate = validateInput("Enter Ticket Release Rate (ms)", scanner);
            customerRetrievalRate = validateInput("Enter Customer Retrieval Rate (ms)", scanner);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An error occurred during input: " + e.getMessage());
        }
        saveConfiguration();
    }

    /**
     * Validates user input to ensure it is a positive integer.
     * If the input is invalid or non-positive, the method will repeatedly prompt
     * the user until a valid value is entered.
     * @param prompt the message to display to the user asking for input
     * @param scanner the Scanner instance used for reading user input
     * @return the valid input value as an integer
     */
    private int validateInput(String prompt, Scanner scanner) {
        int value;
        while (true) {
            System.out.print(prompt + ": ");
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (value > 0) {
                    break;
                } else {
                    System.out.println("Value must be greater than zero. Please try again.");
                }
            } else {
                String invalidInput = scanner.nextLine(); // Consume invalid input
                System.out.println("Invalid input: " + invalidInput + ". Please enter a positive integer.");
            }
        }
        return value;
    }

    /**
     * Saves the current configuration values (max ticket capacity, total tickets,
     * ticket release rate, and customer retrieval rate) to a JSON file.
     * The static field `totalTickets` is manually handled and added to the JSON.
     */
    public void saveConfiguration() {
        try (FileWriter fileWriter = new FileWriter(CONFIGURATION_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maxTicketCapacity", maxTicketCapacity);
            jsonObject.addProperty("totalTickets", totalTickets); // Manually add static field
            jsonObject.addProperty("ticketReleaseRate", ticketReleaseRate);
            jsonObject.addProperty("customerRetrievalRate", customerRetrievalRate);

            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while saving configuration: " + e.getMessage());
        }
    }

    /**
     * Loads the configuration values from the JSON configuration file.
     * This includes reading the max ticket capacity, total tickets,
     * ticket release rate, and customer retrieval rate from the file and
     * updating the respective fields.
     * If the configuration file doesn't exist, a warning is logged.
     */
    public void loadConfiguration() {
        File file = new File(CONFIGURATION_FILE);
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
                this.maxTicketCapacity = jsonObject.get("maxTicketCapacity").getAsInt();
                totalTickets = jsonObject.get("totalTickets").getAsInt(); // Manually set static field
                this.ticketReleaseRate = jsonObject.get("ticketReleaseRate").getAsInt();
                this.customerRetrievalRate = jsonObject.get("customerRetrievalRate").getAsInt();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "An error occurred while loading configuration: " + e.getMessage());
            }
        } else {
            LOGGER.log(Level.WARNING, "Configuration file not found.");
        }
    }

    /**
     * Getter for the max ticket capacity.
     *
     * @return the maximum number of tickets allowed
     */
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    /**
     * Getter for the ticket release rate.
     *
     * @return the rate at which tickets are released (in ms)
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Getter for the customer retrieval rate.
     *
     * @return the rate at which customers retrieve tickets (in ms)
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Getter for the total tickets (static field).
     *
     * @return the total number of tickets
     */
    public static int getTotalTickets() {
        return totalTickets;
    }
}
