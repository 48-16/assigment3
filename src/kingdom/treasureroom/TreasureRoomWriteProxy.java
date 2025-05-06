package kingdom.treasureroom;

import kingdom.valuables.Valuable;
import java.util.List;

/**
 * Protection proxy for read-write access to the treasure room
 */
public class TreasureRoomWriteProxy implements TreasureRoomWrite {
    private final TreasureRoom treasureRoom;
    private boolean hasAccess;

    /**
     * Creates a new write proxy for the treasure room
     * @param treasureRoom The treasure room to proxy
     */
    public TreasureRoomWriteProxy(TreasureRoom treasureRoom) {
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

    @Override
    public void addValuable(Valuable valuable) {
        checkAccess();
        treasureRoom.addValuable(valuable);
    }

    @Override
    public void addValuables(List<Valuable> valuables) {
        checkAccess();
        treasureRoom.addValuables(valuables);
    }

    @Override
    public Valuable takeValuable() {
        checkAccess();
        return treasureRoom.takeValuable();
    }

    @Override
    public List<Valuable> takeValuables(int count) {
        checkAccess();
        return treasureRoom.takeValuables(count);
    }
}