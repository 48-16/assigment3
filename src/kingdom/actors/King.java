package kingdom.actors;

import kingdom.Logger;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomRead;
import kingdom.treasureroom.TreasureRoomWrite;
import kingdom.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class King implements Runnable {
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();
    private final Random random = new Random();
    private int partiesThrown = 0;

    public King(TreasureRoomDoor treasureRoomDoor) {
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log("King", "The King has arrived");

        try {
            while (!Thread.interrupted()) {
                Thread.sleep((long) (Math.random() * 5000) + 5000);

                int partyWorth = random.nextInt(101) + 50;
                logger.log("King", "Planning a party costing " + partyWorth);

                TreasureRoomRead treasureRoomRead = treasureRoomDoor.acquireReadAccess("King");
                boolean enoughValuables = false;

                try {
                    int totalWorth = treasureRoomRead.calculateTotalWorth();
                    enoughValuables = totalWorth >= partyWorth;

                    if (!enoughValuables) {
                        logger.log("King", "Not enough money for a party. Need " + partyWorth +
                                ", but only have " + totalWorth + ". The King is very  disappointed someone gonna die.");
                        continue;
                    }
                } finally {
                    treasureRoomDoor.releaseReadAccess("King");
                }

                TreasureRoomWrite treasureRoom = treasureRoomDoor.acquireWriteAccess("King");

                try {
                    logger.log("King", "Taking valuables for a party");

                    List<Valuable> partyValuables = new ArrayList<>();
                    int collectedWorth = 0;

                    while (collectedWorth < partyWorth) {
                        Valuable valuable = treasureRoom.takeValuable();

                        if (valuable == null) {
                            logger.log("King", "Not enough money, putting back " + partyValuables.size() + " items");
                            treasureRoom.addValuables(partyValuables);
                            enoughValuables = false;
                            break;
                        }

                        partyValuables.add(valuable);
                        collectedWorth += valuable.getWorth();

                        Thread.sleep(200);
                    }

                    if (enoughValuables) {
                        logger.log("King", "Collected valuables worth " + collectedWorth + " for the party");
                    }
                } finally {
                    treasureRoomDoor.releaseWriteAccess("King");
                }

                if (enoughValuables) {
                    partiesThrown++;
                    logger.log("King", "THROWING A GRAND PARTY! (Party #" + partiesThrown + ")");
                    Thread.sleep((long) (Math.random() * 3000) + 2000);
                    logger.log("King", "Party finished. That was delightful!");
                }
            }
        } catch (InterruptedException e) {
            logger.log("King", "The King has retired");
            Thread.currentThread().interrupt();
        }
    }
}