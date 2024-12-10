package lk.iit;

import java.util.logging.Logger;

public class TicketPool {
    private final int maxTicketCapacity;
    private final int totalTickets;
    private int currentTickets = 0;
    private int totalTicketsAdded = 0;
    private int totalTicketsSold = 0;
    private static final Logger LOGGER = Logger.getLogger(TicketPool.class.getName());

    /**
     * Constructor to initialize the TicketPool with a max capacity and total tickets.
     *
     * @param maxTicketCapacity the maximum capacity of tickets that can be added to the pool
     * @param totalTickets the total number of tickets that need to be managed
     */
    public TicketPool(int maxTicketCapacity, int totalTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
    }

    /**
     * Adds tickets to the pool. This method is synchronized to ensure thread safety.
     * If the ticket pool has reached its max capacity or the total tickets have already been added,
     * the vendor will wait. Otherwise, the tickets will be added, and other threads will be notified.
     *
     * @param numTickets the number of tickets to add to the pool
     * @param vendorName the name of the vendor adding the tickets
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized void addTickets(int numTickets, String vendorName) throws InterruptedException {
        while (currentTickets >= totalTickets || totalTicketsAdded >= maxTicketCapacity) {
            if (totalTicketsAdded >= maxTicketCapacity) {
                return;
            }
            LOGGER.info(vendorName + " is waiting to add tickets...");
            wait();
        }

        int availableCapacity = totalTickets - currentTickets;
        int ticketsRemainingToAdd = maxTicketCapacity - totalTicketsAdded;
        int ticketsToAdd = Math.min(numTickets, availableCapacity);
        ticketsToAdd = Math.min(ticketsToAdd, ticketsRemainingToAdd);

        if (ticketsToAdd <= 0) {
            return;
        }

        currentTickets += ticketsToAdd;
        totalTicketsAdded += ticketsToAdd;

        LOGGER.info(vendorName + " added " + ticketsToAdd + " tickets. Current available: " + currentTickets);

        notifyAll();
    }

    /**
     * Allows customers to retrieve tickets from the pool. This method is synchronized to ensure thread safety.
     * If there are no tickets available, the customer will wait. Once a ticket is retrieved, the current ticket count is reduced.
     *
     * @param customerName the name of the customer attempting to retrieve a ticket
     * @return true if a ticket was successfully retrieved, false if all tickets are sold or unavailable
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized boolean retrieveTicket(String customerName) throws InterruptedException {
        while (currentTickets == 0) {
            if (totalTicketsSold >= maxTicketCapacity && totalTicketsAdded >= maxTicketCapacity) {
                return false;
            }
            LOGGER.info(customerName + " is waiting to purchase tickets...");
            wait();
        }

        currentTickets--;
        totalTicketsSold++;
        LOGGER.info(customerName + " purchased a ticket. Current tickets count in the system: " + currentTickets);
        notifyAll();

        return true;
    }

    /**
     * Checks if all tickets have been sold (or if no more tickets are available to sell).
     *
     * @return true if all tickets have been sold, false otherwise
     */
    public synchronized boolean allTicketsSold() {
        return totalTicketsSold >= maxTicketCapacity;
    }
}
