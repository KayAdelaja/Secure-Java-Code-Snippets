import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecureMassAssignment {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public User createUserFromJson(String jsonString) throws IOException {
        // Define a whitelist of allowed fields
        Map<String, Object> allowedFields = new HashMap<>();
        allowedFields.put("username", null);
        allowedFields.put("email", null);

        // Deserialize only allowed fields
        Map<String, Object> inputMap = objectMapper.readValue(jsonString, Map.class);
        inputMap.keySet().retainAll(allowedFields.keySet());

        return objectMapper.convertValue(inputMap, User.class);
    }

    public static void main(String[] args) {
        String jsonInput = "{\"username\": \"testUser\", \"email\": \"test@example.com\", \"role\": \"admin\"}";
        SecureMassAssignment secureMassAssignment = new SecureMassAssignment();

        try {
            User user = secureMassAssignment.createUserFromJson(jsonInput);
            System.out.println("User created: " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private String username;
    private String email;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
