package kingdom.valuables;

/**
 * Interface for valuable items in the kingdom
 */
public interface Valuable {
    /**
     * Get the worth of the valuable
     * @return the value in currency units
     */
    int getWorth();

    /**
     * Get the name of the valuable
     * @return the name of the valuable
     */
    String getName();
}