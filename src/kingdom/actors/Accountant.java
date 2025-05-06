package kingdom.actors;

import kingdom.Logger;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomRead;

/**
 * Accountant that counts the valuables in the treasure room
 */
public class Accountant implements Runnable {
    private final String name;
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();

    /**
     * Creates a new accountant
     * @param name The name of the accountant
     * @param treasureRoomDoor The door to the treasure room
     */
    public Accountant(String name, TreasureRoomDoor treasureRoomDoor) {
        this.name = name;
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log(name, "Started accounting");

        try {
            while (!Thread.interrupted()) {
                // Acquire read access to treasure room
                TreasureRoomRead treasureRoom = treasureRoomDoor.acquireReadAccess(name);

                try {
                    // Count valuables
                    logger.log(name, "Counting valuables in treasure room");

                    // Simulate time to count valuables
                    Thread.sleep((long) (Math.random() * 1000) + 500);

                    int totalWorth = treasureRoom.calculateTotalWorth();
                    int itemCount = treasureRoom.lookAtValuables().size();

                    logger.log(name, "Counted " + itemCount + " valuables worth " + totalWorth + " in the treasure room");
                } finally {
                    // Release read access
                    treasureRoomDoor.releaseReadAccess(name);
                }

                // Rest before next count
                Thread.sleep((long) (Math.random() * 3000) + 2000);
            }
        } catch (InterruptedException e) {
            logger.log(name, "Stopped accounting");
            Thread.currentThread().interrupt();
        }
    }
}