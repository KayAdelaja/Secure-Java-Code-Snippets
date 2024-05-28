import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecureSessionManagement {

    // Create a new secure session
    public HttpSession createSecureSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(30 * 60); // Set session timeout to 30 minutes
        session.setAttribute("csrfToken", generateCSRFToken());
        return session;
    }

    // Invalidate an existing session
    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // Generate a secure CSRF token
    private String generateCSRFToken() {
        return java.util.UUID.randomUUID().toString();
    }

    // Validate CSRF token
    public boolean validateCSRFToken(HttpServletRequest request, String receivedToken) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String csrfToken = (String) session.getAttribute("csrfToken");
            return csrfToken != null && csrfToken.equals(receivedToken);
        }
        return false;
    }

    public static void main(String[] args) {
        // Example usage
    }
}
