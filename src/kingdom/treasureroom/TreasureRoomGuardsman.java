package kingdom.treasureroom;

import kingdom.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Guardsman that controls access to the treasure room (Readers-Writers pattern)
 */
public class TreasureRoomGuardsman implements TreasureRoomDoor {
    private final TreasureRoom treasureRoom;
    private final Logger logger = Logger.getInstance();

    private int readersInside = 0;
    private boolean writerInside = false;
    private final Map<String, TreasureRoomReadProxy> readProxies = new HashMap<>();
    private final Map<String, TreasureRoomWriteProxy> writeProxies = new HashMap<>();

    private int waitingWriters = 0;

    /**
     * Creates a new guardsman for the treasure room
     * @param treasureRoom The treasure room to guard
     */
    public TreasureRoomGuardsman(TreasureRoom treasureRoom) {
        this.treasureRoom = treasureRoom;
    }

    @Override
    public synchronized TreasureRoomRead acquireReadAccess(String actorName) {
        // Wait if there's a writer inside or waiting writers
        while (writerInside || waitingWriters > 0) {
            try {
                logger.log(actorName, "Waiting for read access to treasure room");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(actorName, "Interrupted while waiting for read access");
                return null;
            }
        }

        // Create new proxy or reuse existing one
        TreasureRoomReadProxy proxy;
        if (readProxies.containsKey(actorName)) {
            proxy = readProxies.get(actorName);
        } else {
            proxy = new TreasureRoomReadProxy(treasureRoom);
            readProxies.put(actorName, proxy);
        }

        readersInside++;
        logger.log(actorName, "Acquired read access to treasure room");

        return proxy;
    }

    @Override
    public synchronized void releaseReadAccess(String actorName) {
        if (readersInside > 0) {
            readersInside--;
            // Revoke access on the proxy
            if (readProxies.containsKey(actorName)) {
                readProxies.get(actorName).revokeAccess();
            }

            logger.log(actorName, "Released read access to treasure room");

            // Notify all if no more readers
            if (readersInside == 0) {
                notifyAll();
            }
        }
    }

    @Override
    public synchronized TreasureRoomWrite acquireWriteAccess(String actorName) {
        waitingWriters++;

        // Wait until no readers and no writers
        while (readersInside > 0 || writerInside) {
            try {
                logger.log(actorName, "Waiting for write access to treasure room");
                wait();
            } catch (InterruptedException e) {
                waitingWriters--;
                Thread.currentThread().interrupt();
                logger.log(actorName, "Interrupted while waiting for write access");
                return null;
            }
        }

        waitingWriters--;
        writerInside = true;

        // Create new proxy or reuse existing one
        TreasureRoomWriteProxy proxy;
        if (writeProxies.containsKey(actorName)) {
            proxy = writeProxies.get(actorName);
        } else {
            proxy = new TreasureRoomWriteProxy(treasureRoom);
            writeProxies.put(actorName, proxy);
        }

        logger.log(actorName, "Acquired write access to treasure room");

        return proxy;
    }

    @Override
    public synchronized void releaseWriteAccess(String actorName) {
        if (writerInside) {
            writerInside = false;
            // Revoke access on the proxy
            if (writeProxies.containsKey(actorName)) {
                writeProxies.get(actorName).revokeAccess();
            }

            logger.log(actorName, "Released write access to treasure room");

            // Notify all waiting threads
            notifyAll();
        }
    }
}