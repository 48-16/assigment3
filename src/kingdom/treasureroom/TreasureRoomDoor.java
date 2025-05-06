package kingdom.treasureroom;

/**
 * Interface for the treasure room door, implemented by the Guardsman
 */
public interface TreasureRoomDoor {
    /**
     * Acquire read access to the treasure room
     * @param actorName The name of the actor requesting access
     * @return A read-only interface to the treasure room
     */
    TreasureRoomRead acquireReadAccess(String actorName);

    /**
     * Release read access to the treasure room
     * @param actorName The name of the actor releasing access
     */
    void releaseReadAccess(String actorName);

    /**
     * Acquire write access to the treasure room
     * @param actorName The name of the actor requesting access
     * @return A read-write interface to the treasure room
     */
    TreasureRoomWrite acquireWriteAccess(String actorName);

    /**
     * Release write access to the treasure room
     * @param actorName The name of the actor releasing access
     */
    void releaseWriteAccess(String actorName);
}