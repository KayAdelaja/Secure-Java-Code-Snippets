import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecureSQL {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String USER = "yourusername";
    private static final String PASS = "yourpassword";

    // Secure method to retrieve user information by ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SecureSQL secureSQL = new SecureSQL();
        User user = secureSQL.getUserById(1);
        if (user != null) {
            System.out.println("User: " + user);
        } else {
            System.out.println("User not found.");
        }
    }
}

class User {
    private int userId;
    private String username;
    private String email;

    public User(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
