import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class SecureSSRF {
    private static final Pattern VALID_URL_PATTERN = Pattern.compile("https://trusted\\.domain/.*");

    public String fetchDataFromUrl(String urlString) throws Exception {
        if (!isValidUrl(urlString)) {
            throw new SecurityException("Invalid URL: " + urlString);
        }
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

    private boolean isValidUrl(String urlString) {
        return VALID_URL_PATTERN.matcher(urlString).matches();
    }

    public static void main(String[] args) {
        try {
            SecureSSRF ssrf = new SecureSSRF();
            String data = ssrf.fetchDataFromUrl("https://trusted.domain/resource");
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
