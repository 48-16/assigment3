package kingdom.treasureroom;

import java.util.List;
import kingdom.valuables.Valuable;

/**
 * Interface for read-only access to the treasure room
 */
public interface TreasureRoomRead {
    /**
     * Get a list of all valuables in the treasure room (read-only)
     * @return A list of valuables
     */
    List<Valuable> lookAtValuables();

    /**
     * Calculate the total worth of all valuables in the treasure room
     * @return The total worth
     */
    int calculateTotalWorth();
}