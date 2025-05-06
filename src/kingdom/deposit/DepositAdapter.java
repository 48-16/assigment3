package kingdom.deposit;

import utility.collection.ArrayList;

import kingdom.Logger;
import kingdom.valuables.Valuable;

public class DepositAdapter {
    private final ArrayList<Valuable> valuables;
    private final int maxCapacity;
    private final Logger logger = Logger.getInstance();

    public DepositAdapter(int capacity) {
        this.valuables = new ArrayList<>();
        this.maxCapacity = capacity;
    }

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

    public synchronized Valuable take(String actor) {
        while (valuables.size() == 0) {
            try {
                logger.log(actor, "Waiting to take valuables from deposit (deposit empty)");
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

    public synchronized int size() {
        return valuables.size();
    }

    public synchronized boolean isEmpty() {
        return valuables.size() == 0;
    }
}