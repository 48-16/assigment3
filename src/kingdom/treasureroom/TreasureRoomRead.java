package kingdom.treasureroom;

import java.util.List;
import kingdom.valuables.Valuable;

public interface TreasureRoomRead {
    List<Valuable> lookAtValuables();

    int calculateTotalWorth();
}