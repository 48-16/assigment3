package kingdom.treasureroom;

import dk.ict.eaaa.MyArrayList;
import kingdom.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;

/**
 * The treasure room where valuables are stored
 */
public class TreasureRoom implements TreasureRoomWrite {
    private final MyArrayList<Valuable> valuables;

    /**
     * Creates a new treasure room
     */
    public TreasureRoom() {
        this.valuables = new MyArrayList<>();
    }

    @Override
    public synchronized List<Valuable> lookAtValuables() {
        List<Valuable> copy = new ArrayList<>();
        for (int i = 0; i < valuables.size(); i++) {
            copy.add(valuables.get(i));
        }
        return copy;
    }

    @Override
    public synchronized int calculateTotalWorth() {
        int totalWorth = 0;
        for (int i = 0; i < valuables.size(); i++) {
            totalWorth += valuables.get(i).getWorth();
        }
        return totalWorth;
    }

    @Override
    public synchronized void addValuable(Valuable valuable) {
        valuables.add(valuable);
    }

    @Override
    public synchronized void addValuables(List<Valuable> valuablesToAdd) {
        for (Valuable valuable : valuablesToAdd) {
            valuables.add(valuable);
        }
    }

    @Override
    public synchronized Valuable takeValuable() {
        if (valuables.size() > 0) {
            return valuables.remove(0);
        }
        return null;
    }

    @Override
    public synchronized List<Valuable> takeValuables(int count) {
        List<Valuable> taken = new ArrayList<>();
        int actualCount = Math.min(count, valuables.size());

        for (int i = 0; i < actualCount; i++) {
            taken.add(valuables.remove(0));
        }

        return taken;
    }
}