package kingdom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String actor, String message) {
        String timeStamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timeStamp + "] " + actor + ": " + message);
    }
}