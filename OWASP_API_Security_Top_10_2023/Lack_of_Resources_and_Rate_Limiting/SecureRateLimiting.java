import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class SecureRateLimiting {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/secure-endpoint")
    public String secureEndpoint(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        if (rateLimiterService.isRateLimited(clientIp)) {
            return "Rate limit exceeded. Try again later.";
        }
        return "Request processed successfully.";
    }
}

@Service
class RateLimiterService {
    private final ConcurrentMap<String, RateLimit> rateLimitMap = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 100;
    private static final Duration TIME_WINDOW = Duration.ofHours(1);

    public boolean isRateLimited(String clientIp) {
        RateLimit rateLimit = rateLimitMap.computeIfAbsent(clientIp, k -> new RateLimit());
        synchronized (rateLimit) {
            if (rateLimit.getRequests() < MAX_REQUESTS) {
                rateLimit.incrementRequests();
                return false;
            } else {
                if (Duration.between(rateLimit.getTimestamp(), LocalDateTime.now()).compareTo(TIME_WINDOW) > 0) {
                    rateLimit.resetRequests();
                    rateLimit.incrementRequests();
                    return false;
                }
                return true;
            }
        }
    }
}

class RateLimit {
    private int requests;
    private LocalDateTime timestamp;

    public RateLimit() {
        this.requests = 0;
        this.timestamp = LocalDateTime.now();
    }

    public int getRequests() {
        return requests;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void incrementRequests() {
        this.requests++;
    }

    public void resetRequests() {
        this.requests = 0;
        this.timestamp = LocalDateTime.now();
    }
}
