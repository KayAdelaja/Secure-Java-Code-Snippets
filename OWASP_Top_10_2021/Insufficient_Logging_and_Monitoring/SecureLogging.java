import java.util.logging.Level;
import java.util.logging.Logger;

public class SecureLogging {
    private static final Logger LOGGER = Logger.getLogger(SecureLogging.class.getName());

    public void logUserAction(String username, String action) {
        if (username == null || action == null) {
            LOGGER.log(Level.WARNING, "Attempted to log null username or action.");
            return;
        }
        LOGGER.log(Level.INFO, "User {0} performed action: {1}", new Object[]{username, action});
    }

    public static void main(String[] args) {
        SecureLogging secureLogging = new SecureLogging();
        secureLogging.logUserAction("testUser", "login");
    }
}
