package kingdom.actors;

import kingdom.Logger;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomRead;
import kingdom.treasureroom.TreasureRoomWrite;
import kingdom.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The King who throws parties using valuables from the treasure room
 */
public class King implements Runnable {
    private final TreasureRoomDoor treasureRoomDoor;
    private final Logger logger = Logger.getInstance();
    private final Random random = new Random();
    private int partiesThrown = 0;

    /**
     * Creates a new king
     * @param treasureRoomDoor The door to the treasure room
     */
    public King(TreasureRoomDoor treasureRoomDoor) {
        this.treasureRoomDoor = treasureRoomDoor;
    }

    @Override
    public void run() {
        logger.log("King", "The King has arrived");

        try {
            while (!Thread.interrupted()) {
                // Sleep for a while before planning a party
                Thread.sleep((long) (Math.random() * 5000) + 5000);

                // Generate random party cost
                int partyWorth = random.nextInt(101) + 50; // Between 50 and 150
                logger.log("King", "Planning a party costing " + partyWorth);

                // Check if there are enough valuables
                TreasureRoomRead treasureRoomRead = treasureRoomDoor.acquireReadAccess("King");
                boolean enoughValuables = false;

                try {
                    int totalWorth = treasureRoomRead.calculateTotalWorth();
                    enoughValuables = totalWorth >= partyWorth;

                    if (!enoughValuables) {
                        logger.log("King", "Not enough valuables for a party. Need " + partyWorth +
                                ", but only have " + totalWorth + ". The King is disappointed.");
                        continue;
                    }
                } finally {
                    treasureRoomDoor.releaseReadAccess("King");
                }

                // Acquire write access to take valuables
                TreasureRoomWrite treasureRoom = treasureRoomDoor.acquireWriteAccess("King");

                try {
                    logger.log("King", "Taking valuables for a party");

                    // Take valuables for the party
                    List<Valuable> partyValuables = new ArrayList<>();
                    int collectedWorth = 0;

                    while (collectedWorth < partyWorth) {
                        Valuable valuable = treasureRoom.takeValuable();

                        if (valuable == null) {
                            // Not enough valuables, put back the ones taken
                            logger.log("King", "Not enough valuables, putting back " + partyValuables.size() + " items");
                            treasureRoom.addValuables(partyValuables);
                            enoughValuables = false;
                            break;
                        }

                        partyValuables.add(valuable);
                        collectedWorth += valuable.getWorth();

                        // Simulate taking time to select valuables
                        Thread.sleep(200);
                    }

                    if (enoughValuables) {
                        logger.log("King", "Collected valuables worth " + collectedWorth + " for the party");
                    }
                } finally {
                    treasureRoomDoor.releaseWriteAccess("King");
                }

                // Throw the party if enough valuables were collected
                if (enoughValuables) {
                    partiesThrown++;
                    logger.log("King", "THROWING A GRAND PARTY! (Party #" + partiesThrown + ")");
                    // Party time!
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