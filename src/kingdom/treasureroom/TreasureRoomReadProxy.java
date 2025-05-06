package kingdom.treasureroom;

import kingdom.valuables.Valuable;
import java.util.List;

public class TreasureRoomReadProxy implements TreasureRoomRead {
    private final TreasureRoom treasureRoom;
    private boolean hasAccess;

    public TreasureRoomReadProxy(TreasureRoom treasureRoom) {
        this.treasureRoom = treasureRoom;
        this.hasAccess = true;
    }

    public void revokeAccess() {
        this.hasAccess = false;
    }

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