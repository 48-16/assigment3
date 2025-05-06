package kingdom.treasureroom;

import kingdom.valuables.Valuable;
import java.util.List;

/**
 * Interface for read-write access to the treasure room
 */
public interface TreasureRoomWrite extends TreasureRoomRead {
    /**
     * Add a valuable to the treasure room
     * @param valuable The valuable to add
     */
    void addValuable(Valuable valuable);

    /**
     * Add a list of valuables to the treasure room
     * @param valuables The list of valuables to add
     */
    void addValuables(List<Valuable> valuables);

    /**
     * Take a valuable from the treasure room
     * @return The valuable taken, or null if the treasure room is empty
     */
    Valuable takeValuable();

    /**
     * Take a specific number of valuables from the treasure room
     * @param count The number of valuables to take
     * @return A list of valuables taken
     */
    List<Valuable> takeValuables(int count);
}