package lk.iit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;
    private static final Logger LOGGER = Logger.getLogger(Customer.class.getName());

    /**
     * Constructor to initialize the Customer object.
     *
     * @param ticketPool the TicketPool instance where tickets are stored
     * @param customerRetrievalRate the rate (in ms) at which the customer retrieves tickets
     * @param customerName the name of the customer
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName = customerName;
    }

    /**
     * The run method executed when the customer thread starts. This method continuously
     * attempts to retrieve tickets from the TicketPool at the specified retrieval rate.
     * The customer stops when no tickets are available or when interrupted.
     */
    @Override
    public void run() {
        try {
            while (true) {
                boolean ticketRetrieved = ticketPool.retrieveTicket(customerName);
                if (!ticketRetrieved) {
                    break;
                }
                Thread.sleep(customerRetrievalRate);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, customerName + " has been interrupted.");
        }
        LOGGER.log(Level.WARNING, customerName + " has finished purchasing tickets.");
    }
}
