public class SecureFunctionality {
    private final boolean isAdmin;

    public SecureFunctionality(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void performSensitiveAction() {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access to sensitive functionality.");
        }
        // Perform the sensitive action securely
    }

    public static void main(String[] args) {
        SecureFunctionality functionality = new SecureFunctionality(true);
        functionality.performSensitiveAction();
    }
}
