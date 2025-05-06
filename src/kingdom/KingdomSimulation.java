package kingdom;

import kingdom.actors.*;
import kingdom.deposit.DepositAdapter;
import kingdom.treasureroom.TreasureRoom;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomGuardsman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KingdomSimulation {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("System", "Creating Kingdom ");

        Mine mine = new Mine();

        DepositAdapter deposit = new DepositAdapter(20);

        TreasureRoom treasureRoom = new TreasureRoom();
        TreasureRoomDoor guardsman = new TreasureRoomGuardsman(treasureRoom);

        King king = new King(guardsman);

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            ValuableMiner miner = new ValuableMiner("Miner " + i, mine, deposit);
            threads.add(new Thread(miner));
        }

        for (int i = 1; i <= 2; i++) {
            ValuableTransporter transporter = new ValuableTransporter("Transporter " + i, deposit, guardsman);
            threads.add(new Thread(transporter));
        }

        for (int i = 1; i <= 2; i++) {
            Accountant accountant = new Accountant("Accountant " + i, guardsman);
            threads.add(new Thread(accountant));
        }

        threads.add(new Thread(king));

        for (Thread thread : threads) {
            thread.start();
        }

        logger.log("System", "Kingdom  is living. Press Enter to stop.");

        new Scanner(System.in).nextLine();

        logger.log("System", "Destroing Kingdom ");

        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                logger.log("System", "Interrupted while waiting for thread to finish.");
            }
        }

        logger.log("System", "Kingdom  destroyed.");
    }
}