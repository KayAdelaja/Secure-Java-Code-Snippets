import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecureHashingAlgorithm {

    // Generate a secure hash using SHA-256
    public String generateSecureHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] salt = getSalt();
        md.update(salt);
        byte[] hashedPassword = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Generate a random salt using a secure random number generator
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) {
        try {
            SecureHashingAlgorithm hasher = new SecureHashingAlgorithm();
            String hash = hasher.generateSecureHash("securePassword");
            System.out.println("Secure Hash: " + hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
