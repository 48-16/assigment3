package kingdom.treasureroom;

import kingdom.Logger;

import java.util.HashMap;
import java.util.Map;

public class TreasureRoomGuardsman implements TreasureRoomDoor {
    private final TreasureRoom treasureRoom;
    private final Logger logger = Logger.getInstance();

    private int readersInside = 0;
    private boolean writerInside = false;
    private final Map<String, TreasureRoomReadProxy> readProxies = new HashMap<>();
    private final Map<String, TreasureRoomWriteProxy> writeProxies = new HashMap<>();

    private int waitingWriters = 0;

    public TreasureRoomGuardsman(TreasureRoom treasureRoom) {
        this.treasureRoom = treasureRoom;
    }

    @Override
    public synchronized TreasureRoomRead acquireReadAccess(String actorName) {
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

            if (readersInside == 0) {
                notifyAll();
            }
        }
    }

    @Override
    public synchronized TreasureRoomWrite acquireWriteAccess(String actorName) {
        waitingWriters++;

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
            if (writeProxies.containsKey(actorName)) {
                writeProxies.get(actorName).revokeAccess();
            }

            logger.log(actorName, "Released write access to treasure room");

            notifyAll();
        }
    }
}