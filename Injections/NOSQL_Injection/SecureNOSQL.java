import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class SecureNoSQL {

    private static final String DB_NAME = "yourdatabase";
    private static final String COLLECTION_NAME = "users";
    private MongoClient mongoClient;

    public SecureNoSQL() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    // Secure method to retrieve user information by username using parameterized queries
    public Document getUserByUsername(String username) {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        
        Document query = new Document("username", username);
        return collection.find(query).first();
    }

    public static void main(String[] args) {
        SecureNoSQL secureNoSQL = new SecureNoSQL();
        Document user = secureNoSQL.getUserByUsername("testUser");

        if (user != null) {
            System.out.println("User: " + user.toJson());
        } else {
            System.out.println("User not found.");
        }
    }
}
