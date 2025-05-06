package kingdom.actors;

import kingdom.Logger;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomRead;

public class Accountant implements Runnable {
    private final String name;
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();

    public Accountant(String name, TreasureRoomDoor treasureRoomDoor) {
        this.name = name;
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log(name, "Started accounting");

        try {
            while (!Thread.interrupted()) {
                TreasureRoomRead treasureRoom = treasureRoomDoor.acquireReadAccess(name);

                try {
                    logger.log(name, "Counting valuables in treasure room");

                    Thread.sleep((long) (Math.random() * 1000) + 500);

                    int totalWorth = treasureRoom.calculateTotalWorth();
                    int itemCount = treasureRoom.lookAtValuables().size();

                    logger.log(name, "Counted " + itemCount + " valuables worth " + totalWorth + " in the treasure room");
                } finally {
                    treasureRoomDoor.releaseReadAccess(name);
                }

                Thread.sleep((long) (Math.random() * 3000) + 2000);
            }
        } catch (InterruptedException e) {
            logger.log(name, "Stopped accounting");
            Thread.currentThread().interrupt();
        }
    }
}