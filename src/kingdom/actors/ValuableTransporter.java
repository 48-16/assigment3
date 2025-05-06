package kingdom.actors;

import kingdom.Logger;
import kingdom.deposit.DepositAdapter;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomWrite;
import kingdom.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ValuableTransporter implements Runnable {
    private final String name;
    private final DepositAdapter deposit;
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();
    private final Random random = new Random();

    public ValuableTransporter(String name, DepositAdapter deposit, TreasureRoomDoor treasureRoomDoor) {
        this.name = name;
        this.deposit = deposit;
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log(name, "Started moving valuables");

        try {
            while (!Thread.interrupted()) {
                int targetWorth = random.nextInt(151) + 50;
                logger.log(name, "Planning to drive valuables worth at least " + targetWorth);

                List<Valuable> collected = new ArrayList<>();
                int collectedWorth = 0;

                while (collectedWorth < targetWorth) {
                    Valuable valuable = deposit.take(name);
                    collected.add(valuable);
                    collectedWorth += valuable.getWorth();
                    logger.log(name, "Collected " + valuable.getName() + " (Total: " + collectedWorth + "/" + targetWorth + ")");
                }

                logger.log(name, "Driving valuables worth " + collectedWorth + " to treasure room");

                Thread.sleep((long) (Math.random() * 1000) + 500);

                TreasureRoomWrite treasureRoom = treasureRoomDoor.acquireWriteAccess(name);
                try {
                    logger.log(name, "Storing " + collected.size() + " valuables in treasure room");

                    for (Valuable valuable : collected) {
                        Thread.sleep(100);
                        treasureRoom.addValuable(valuable);
                        logger.log(name, "Stored " + valuable.getName() + " in treasure room");
                    }
                } finally {
                    treasureRoomDoor.releaseWriteAccess(name);
                }

                Thread.sleep((long) (Math.random() * 2000) + 1000);
            }
        } catch (InterruptedException e) {
            logger.log(name, "Stopped transporting valuables");
            Thread.currentThread().interrupt();
        }
    }
}