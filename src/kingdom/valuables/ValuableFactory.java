package kingdom.valuables;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ValuableFactory {
    private static final Map<String, Valuable> valuableInstances = new HashMap<>();
    private static final Random random = new Random();

    public static final String DIAMOND = "Diamond";
    public static final String GOLD_NUGGET = "Gold Nugget";
    public static final String JEWEL = "Jewelery";
    public static final String RUBY = "Ruby";
    public static final String COAL = "Coal";

    private static final int DIAMOND_WORTH = 50;
    private static final int GOLD_NUGGET_WORTH = 30;
    private static final int JEWEL_WORTH = 40;
    private static final int RUBY_WORTH = 45;
    private static final int COAL_WORTH = 5;

    public static synchronized Valuable getValuable(String type) {
        if (!valuableInstances.containsKey(type)) {
            switch (type) {
                case DIAMOND:
                    valuableInstances.put(type, new ValuableImpl(DIAMOND, DIAMOND_WORTH));
                    break;
                case GOLD_NUGGET:
                    valuableInstances.put(type, new ValuableImpl(GOLD_NUGGET, GOLD_NUGGET_WORTH));
                    break;
                case JEWEL:
                    valuableInstances.put(type, new ValuableImpl(JEWEL, JEWEL_WORTH));
                    break;
                case RUBY:
                    valuableInstances.put(type, new ValuableImpl(RUBY, RUBY_WORTH));
                    break;
                case COAL:
                    valuableInstances.put(type, new ValuableImpl(COAL, COAL_WORTH));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown valuable type: " + type);
            }
        }
        return valuableInstances.get(type);
    }

    public static Valuable getRandomValuable() {
        int randomValue = random.nextInt(100);

        if (randomValue < 10) {
            return getValuable(DIAMOND);
        } else if (randomValue < 30) {
            return getValuable(RUBY);
        } else if (randomValue < 55) {
            return getValuable(JEWEL);
        } else if (randomValue < 85) {
            return getValuable(GOLD_NUGGET);
        } else {
            return getValuable(COAL);
        }
    }

    private static class ValuableImpl implements Valuable {
        private final String name;
        private final int worth;

        public ValuableImpl(String name, int worth) {
            this.name = name;
            this.worth = worth;
        }

        @Override
        public int getWorth() {
            return worth;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name + " (Worth: " + worth + ")";
        }
    }
}