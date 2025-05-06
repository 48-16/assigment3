package kingdom;

import kingdom.valuables.Valuable;
import kingdom.valuables.ValuableFactory;


public class Mine {

    public Valuable excavateValuable() {
        return ValuableFactory.getRandomValuable();
    }
}