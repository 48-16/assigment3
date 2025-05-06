package kingdom.treasureroom;

import kingdom.valuables.Valuable;
import java.util.List;

/**
 * Protection proxy for read-only access to the treasure room
 */
public class TreasureRoomReadProxy implements TreasureRoomRead {
    private final TreasureRoom treasureRoom;
    private boolean hasAccess;

    /**
     * Creates a new read proxy for the treasure room
     * @param treasureRoom The treasure room to proxy
     */
    public TreasureRoomReadProxy(TreasureRoom treasureRoom) {
        this.treasureRoom = treasureRoom;
        this.hasAccess = true;
    }

    /**
     * Revoke access to the treasure room
     */
    public void revokeAccess() {
        this.hasAccess = false;
    }

    /**
     * Check if the proxy has access
     * @throws SecurityException if access has been revoked
     */
    private void checkAccess() {
        if (!hasAccess) {
            throw new SecurityException("Access to treasure room has been revoked");
        }
    }

    @Override
    public List<Valuable> lookAtValuables() {
        checkAccess();
        return treasureRoom.lookAtValuables();
    }

    @Override
    public int calculateTotalWorth() {
        checkAccess();
        return treasureRoom.calculateTotalWorth();
    }
}