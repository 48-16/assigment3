package kingdom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton Logger class for logging actions in the Kingdom simulation
 */
public class Logger {
    private static Logger instance;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Private constructor to prevent instantiation
    private Logger() {
    }

    /**
     * Get the singleton instance of the logger
     * @return Logger instance
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Log an action with actor and message
     * @param actor The actor performing the action
     * @param message The action message
     */
    public void log(String actor, String message) {
        String timeStamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timeStamp + "] " + actor + ": " + message);
    }
}