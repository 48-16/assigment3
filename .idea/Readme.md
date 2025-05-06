# Kingdom Simulation

This project implements a Kingdom simulation with multiple threads and design patterns as specified in the requirements.

## Overview

The Kingdom simulation consists of:
- A **King** who throws parties using valuables from the Treasure Room
- **Miners** who extract valuables from the Mine
- **ValuableTransporters** who move valuables from the Deposit to the Treasure Room
- **Accountants** who count the valuables in the Treasure Room
- A **Guardsman** who controls access to the Treasure Room

## Design Patterns Implemented

1. **Singleton Pattern**
    - Implemented in the `Logger` class
    - Ensures a single instance for logging throughout the application

2. **Multiton Pattern**
    - Implemented in `ValuableFactory` for different types of valuables
    - Provides controlled instances of `Diamond`, `GoldNugget`, `Jewel`, `Ruby`, and `WoodenCoin`

3. **Adapter Pattern**
    - `DepositAdapter` adapts the provided `MyArrayList` to create a blocking queue
    - Handles thread synchronization for producer-consumer pattern

4. **Producer-Consumer Pattern**
    - `Miners` are producers adding valuables to the `Deposit`
    - `ValuableTransporters` are consumers taking valuables from the `Deposit`

5. **Readers-Writers Pattern**
    - Implemented for the `TreasureRoom` access control
    - Multiple readers (`Accountants`) can access simultaneously
    - Only one writer (`King` or `ValuableTransporter`) can access at a time
    - Writers have priority over readers

6. **Proxy Pattern**
    - `TreasureRoomReadProxy` and `TreasureRoomWriteProxy` serve as protection proxies
    - Control access to the `TreasureRoom`
    - Provide read-only or read-write access as appropriate
    - Check for valid access before delegating to the real `TreasureRoom`

## Project Structure

```
kingdom/
├── Logger.java (Singleton)
├── Mine.java
├── KingdomSimulation.java (Main class)
├── actors/
│   ├── King.java
│   ├── Accountant.java
│   ├── ValuableMiner.java
│   └── ValuableTransporter.java
├── deposit/
│   └── DepositAdapter.java (Adapter)
├── treasureroom/
│   ├── TreasureRoom.java
│   ├── TreasureRoomDoor.java (Interface)
│   ├── TreasureRoomRead.java (Interface)
│   ├── TreasureRoomWrite.java (Interface)
│   ├── TreasureRoomGuardsman.java (Readers-Writers)
│   ├── TreasureRoomReadProxy.java (Proxy)
│   └── TreasureRoomWriteProxy.java (Proxy)
├── valuables/
│   ├── Valuable.java (Interface)
│   └── ValuableFactory.java (Multiton)
└── test/
    └── MyArrayListTest.java (JUnit Tests)
```

## How to Run the Simulation

1. Make sure you have the required JAR file in your classpath:
    - MyArrayList-0.1.jar (http://ict-engineering.dk/jar/MyArrayList-0.1.jar)

2. Compile the project:
   ```bash
   javac -cp ".:MyArrayList-0.1.jar:junit-4.13.jar:hamcrest-core-1.3.jar" kingdom/*.java kingdom/*/*.java test/*.java
   ```

3. Run the simulation:
   ```bash
   java -cp ".:MyArrayList-0.1.jar" kingdom.KingdomSimulation
   ```

4. To run the JUnit tests:
   ```bash
   java -cp ".:MyArrayList-0.1.jar:junit-4.13.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore test.MyArrayListTest
   ```

## Simulation Details

- **Miners** continuously extract random valuables from the Mine and add them to the Deposit
- **ValuableTransporters** collect valuables from the Deposit until reaching a random target worth, then transport them to the Treasure Room
- **Accountants** periodically count the valuables in the Treasure Room and report the total worth
- The **King** plans parties with a random cost and takes valuables from the Treasure Room if there are enough

Press Enter to stop the simulation at any time.