package kingdom.treasureroom;

import kingdom.valuables.Valuable;
import java.util.List;


public interface TreasureRoomWrite extends TreasureRoomRead {
    void addValuable(Valuable valuable);

    void addValuables(List<Valuable> valuables);

    Valuable takeValuable();

    List<Valuable> takeValuables(int count);
}