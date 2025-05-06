package kingdom.actors;

import kingdom.Logger;
import kingdom.Mine;
import kingdom.deposit.DepositAdapter;
import kingdom.valuables.Valuable;

/**
 * Miner that extracts valuables from the mine and adds them to the deposit
 */
public class ValuableMiner implements Runnable {
    private final Mine mine;
    private final DepositAdapter deposit;
    private final String name;
    private final Logger logger = Logger.getInstance();

    /**
     * Creates a new miner
     * @param name The name of the miner
     * @param mine The mine to extract valuables from
     * @param deposit The deposit to add valuables to
     */
    public ValuableMiner(String name, Mine mine, DepositAdapter deposit) {
        this.name = name;
        this.mine = mine;
        this.deposit = deposit;
    }

    @Override
    public void run() {
        logger.log(name, "Started mining");

        try {
            while (!Thread.interrupted()) {
                // Mine a valuable
                logger.log(name, "Mining...");
                Thread.sleep((long) (Math.random() * 1000) + 500);

                Valuable valuable = mine.excavateValuable();
                logger.log(name, "Found a " + valuable.getName());

                // Add to deposit
                deposit.add(valuable, name);

                // Sleep for a while before mining again
                Thread.sleep((long) (Math.random() * 500) + 100);
            }
        } catch (InterruptedException e) {
            logger.log(name, "Stopped mining");
            Thread.currentThread().interrupt();
        }
    }
}