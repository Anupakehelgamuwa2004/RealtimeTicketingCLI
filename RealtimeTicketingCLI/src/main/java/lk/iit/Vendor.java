package lk.iit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int numTicketsToAddEachTime = Configuration.totalTickets;
    private final String vendorName;
    private static final Logger LOGGER = Logger.getLogger(Vendor.class.getName());

    /**
     * Constructor to initialize the Vendor object.
     * @param ticketPool the TicketPool instance to which tickets will be added
     * @param ticketReleaseRate the rate at which tickets will be added (in milliseconds)
     * @param vendorName the name of the vendor
     */
    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorName = vendorName;
    }

    /**
     * The run method executed when the vendor thread starts. This method continuously adds tickets
     * to the TicketPool until all tickets are sold.
     */
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (ticketPool) {
                    if (ticketPool.allTicketsSold()) {
                        break;
                    }
                }
                ticketPool.addTickets(numTicketsToAddEachTime, vendorName);
                Thread.sleep(ticketReleaseRate);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, vendorName + " has been interrupted.");
        }
        LOGGER.log(Level.WARNING, vendorName + " has finished adding tickets.");
    }
}
