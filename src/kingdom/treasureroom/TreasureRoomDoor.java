package kingdom.treasureroom;

public interface TreasureRoomDoor {
    TreasureRoomRead acquireReadAccess(String actorName);

    void releaseReadAccess(String actorName);

    TreasureRoomWrite acquireWriteAccess(String actorName);

    void releaseWriteAccess(String actorName);
}