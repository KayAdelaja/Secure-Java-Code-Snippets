import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class SecureIVKeyPair {

    // Encrypt a message using AES with a unique IV for each encryption
    public String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = generateIv();
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        byte[] ivAndEncrypted = concatenate(iv.getIV(), encrypted);
        return Base64.getEncoder().encodeToString(ivAndEncrypted);
    }

    // Decrypt a message using AES, extracting the IV from the encrypted message
    public String decrypt(String cipherText, SecretKey key) throws Exception {
        byte[] ivAndEncrypted = Base64.getDecoder().decode(cipherText);
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(ivAndEncrypted, 0, 16));
        byte[] encrypted = Arrays.copyOfRange(ivAndEncrypted, 16, ivAndEncrypted.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    // Generate a new AES key
    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 256-bit AES key
        return keyGen.generateKey();
    }

    // Generate a new initialization vector (IV)
    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    // Concatenate IV and encrypted message
    private byte[] concatenate(byte[] iv, byte[] encrypted) {
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        return result;
    }

    public static void main(String[] args) {
        try {
            SecureIVKeyPair secureIVKeyPair = new SecureIVKeyPair();
            SecretKey key = secureIVKeyPair.generateKey();

            String plainText = "This is a secure message.";
            String cipherText = secureIVKeyPair.encrypt(plainText, key);
            System.out.println("Encrypted: " + cipherText);

            String decryptedText = secureIVKeyPair.decrypt(cipherText, key);
            System.out.println("Decrypted: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
