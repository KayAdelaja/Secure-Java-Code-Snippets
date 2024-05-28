import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureFunctionalityExposed {

    @Autowired
    private SecureService secureService;

    @GetMapping("/secure-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Ensure only users with ADMIN role can access this endpoint
    public String getSecureData() {
        return secureService.getSecureData();
    }
}

@Service
class SecureService {
    public String getSecureData() {
        // Return sensitive information securely
        return "This is secure data available only to admins.";
    }
}
