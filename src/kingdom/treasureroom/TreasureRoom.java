package kingdom.treasureroom;

import utility.collection.ArrayList;
import kingdom.valuables.Valuable;

import java.util.List;

public class TreasureRoom implements TreasureRoomWrite {
    private final ArrayList<Valuable> valuables;

    public TreasureRoom() {
        this.valuables = new ArrayList<>();
    }

    @Override
    public synchronized List<Valuable> lookAtValuables() {
        List<Valuable> copy = new java.util.ArrayList<>();
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
        List<Valuable> taken = new java.util.ArrayList<>();
        int actualCount = Math.min(count, valuables.size());

        for (int i = 0; i < actualCount; i++) {
            taken.add(valuables.remove(0));
        }

        return taken;
    }
}