import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Base64;

public class SecureDeserialization {
    public static Object deserialize(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            // Implement validation or filtering logic if needed
            return ois.readObject();
        }
    }

    public static void main(String[] args) {
        try {
            String serializedObject = "rO0ABXNyAC5jb20uZXhhbXBsZS5TZWN1cmVEZXNlcmlhbGl6YXRpb24kU2VyaWFsaXphYmxlQ2xhc3MAAAAAAAAAAQIAAHhw";
            Object obj = deserialize(serializedObject);
            System.out.println(obj);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class SerializableClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private String data;

    public SerializableClass(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SerializableClass{" + "data='" + data + '\'' + '}';
    }
}
