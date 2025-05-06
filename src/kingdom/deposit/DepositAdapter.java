package kingdom.deposit;

import utility.collection.ArrayList;

import kingdom.Logger;
import kingdom.valuables.Valuable;

/**
 * Adapter for MyArrayList to be used as a blocking queue for the Deposit
 */
public class DepositAdapter {
    private final ArrayList<Valuable> valuables;
    private final int maxCapacity;
    private final Logger logger = Logger.getInstance();

    /**
     * Creates a new DepositAdapter with specified capacity
     * @param capacity The maximum capacity of the deposit
     */
    public DepositAdapter(int capacity) {
        this.valuables = new ArrayList<>();
        this.maxCapacity = capacity;
    }

    /**
     * Add a valuable to the deposit - blocks if deposit is full
     * @param valuable The valuable to add
     * @param actor The actor adding the valuable
     */
    public synchronized void add(Valuable valuable, String actor) {
        while (valuables.size() >= maxCapacity) {
            try {
                logger.log(actor, "Waiting to add " + valuable.getName() + " to deposit (deposit full)");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        valuables.add(valuable);
        logger.log(actor, "Added " + valuable.getName() + " to deposit");
        notifyAll();
    }

    /**
     * Take a valuable from the deposit - blocks if deposit is empty
     * @param actor The actor taking the valuable
     * @return The removed valuable
     */
    public synchronized Valuable take(String actor) {
        while (valuables.size() == 0) {
            try {
                logger.log(actor, "Waiting to take valuable from deposit (deposit empty)");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Valuable valuable = valuables.remove(0);
        logger.log(actor, "Took " + valuable.getName() + " from deposit");
        notifyAll();
        return valuable;
    }

    /**
     * Get the current size of the deposit
     * @return The number of valuables in the deposit
     */
    public synchronized int size() {
        return valuables.size();
    }

    /**
     * Check if the deposit is empty
     * @return true if the deposit is empty, false otherwise
     */
    public synchronized boolean isEmpty() {
        return valuables.size() == 0;
    }
}