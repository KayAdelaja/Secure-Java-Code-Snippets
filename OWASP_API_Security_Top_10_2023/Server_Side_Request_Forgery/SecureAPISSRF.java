import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

@RestController
public class SecureAPISSRF {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/fetch-data")
    public String fetchData(@RequestParam String url) {
        if (!externalApiService.isValidUrl(url)) {
            return "Invalid URL.";
        }
        try {
            return externalApiService.fetchData(url);
        } catch (Exception e) {
            return "Error fetching data.";
        }
    }
}

@Service
class ExternalApiService {
    private static final Pattern VALID_URL_PATTERN = Pattern.compile("https://trusted\\.domain/.*");

    public boolean isValidUrl(String urlString) {
        return VALID_URL_PATTERN.matcher(urlString).matches();
    }

    public String fetchData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        }
    }
}
