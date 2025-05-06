package kingdom;

import kingdom.actors.*;
import kingdom.deposit.DepositAdapter;
import kingdom.treasureroom.TreasureRoom;
import kingdom.treasureroom.TreasureRoomDoor;
import kingdom.treasureroom.TreasureRoomGuardsman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to run the Kingdom simulation
 */
public class KingdomSimulation {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("System", "Starting Kingdom Simulation");

        // Create the mine
        Mine mine = new Mine();

        // Create the deposit
        DepositAdapter deposit = new DepositAdapter(20); // Max capacity of 20

        // Create the treasure room and guardsman
        TreasureRoom treasureRoom = new TreasureRoom();
        TreasureRoomDoor guardsman = new TreasureRoomGuardsman(treasureRoom);

        // Create actors
        King king = new King(guardsman);

        List<Thread> threads = new ArrayList<>();

        // Create miners
        for (int i = 1; i <= 3; i++) {
            ValuableMiner miner = new ValuableMiner("Miner " + i, mine, deposit);
            threads.add(new Thread(miner));
        }

        // Create valuable transporters
        for (int i = 1; i <= 2; i++) {
            ValuableTransporter transporter = new ValuableTransporter("Transporter " + i, deposit, guardsman);
            threads.add(new Thread(transporter));
        }

        // Create accountants
        for (int i = 1; i <= 2; i++) {
            Accountant accountant = new Accountant("Accountant " + i, guardsman);
            threads.add(new Thread(accountant));
        }

        // Add the king
        threads.add(new Thread(king));

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        logger.log("System", "Kingdom simulation is running. Press Enter to stop.");

        // Wait for Enter key to stop simulation
        new Scanner(System.in).nextLine();

        logger.log("System", "Stopping Kingdom Simulation");

        // Interrupt all threads
        for (Thread thread : threads) {
            thread.interrupt();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                logger.log("System", "Interrupted while waiting for thread to finish.");
            }
        }

        logger.log("System", "Kingdom Simulation stopped.");
    }
}