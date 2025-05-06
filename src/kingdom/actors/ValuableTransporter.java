package kingdom.actors;

import kingdom.Logger;
import kingdom.deposit.DepositAdapter;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomWrite;
import kingdom.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Transports valuables from the deposit to the treasure room
 */
public class ValuableTransporter implements Runnable {
    private final String name;
    private final DepositAdapter deposit;
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();
    private final Random random = new Random();

    /**
     * Creates a new valuable transporter
     * @param name The name of the transporter
     * @param deposit The deposit to take valuables from
     * @param treasureRoomDoor The door to the treasure room
     */
    public ValuableTransporter(String name, DepositAdapter deposit, TreasureRoomDoor treasureRoomDoor) {
        this.name = name;
        this.deposit = deposit;
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log(name, "Started transporting valuables");

        try {
            while (!Thread.interrupted()) {
                // Generate random target worth
                int targetWorth = random.nextInt(151) + 50; // Between 50 and 200
                logger.log(name, "Planning to transport valuables worth at least " + targetWorth);

                // Collect valuables from deposit
                List<Valuable> collected = new ArrayList<>();
                int collectedWorth = 0;

                while (collectedWorth < targetWorth) {
                    Valuable valuable = deposit.take(name);
                    collected.add(valuable);
                    collectedWorth += valuable.getWorth();
                    logger.log(name, "Collected " + valuable.getName() + " (Total: " + collectedWorth + "/" + targetWorth + ")");
                }

                logger.log(name, "Transporting valuables worth " + collectedWorth + " to treasure room");

                // Travel to treasure room (simulate with sleep)
                Thread.sleep((long) (Math.random() * 1000) + 500);

                // Add valuables to treasure room
                TreasureRoomWrite treasureRoom = treasureRoomDoor.acquireWriteAccess(name);
                try {
                    logger.log(name, "Adding " + collected.size() + " valuables to treasure room");

                    // Simulate time to add each valuable
                    for (Valuable valuable : collected) {
                        Thread.sleep(100);
                        treasureRoom.addValuable(valuable);
                        logger.log(name, "Added " + valuable.getName() + " to treasure room");
                    }
                } finally {
                    treasureRoomDoor.releaseWriteAccess(name);
                }

                // Rest before next transport
                Thread.sleep((long) (Math.random() * 2000) + 1000);
            }
        } catch (InterruptedException e) {
            logger.log(name, "Stopped transporting valuables");
            Thread.currentThread().interrupt();
        }
    }
}