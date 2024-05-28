public class SecureDesign {
    public void processTransaction(User user, Transaction transaction) {
        if (!user.isAuthorizedFor(transaction)) {
            throw new SecurityException("User not authorized for this transaction.");
        }
        // Process the transaction securely
    }
}

class User {
    private String username;
    private Set<String> authorizedTransactions;

    public User(String username, Set<String> authorizedTransactions) {
        this.username = username;
        this.authorizedTransactions = authorizedTransactions;
    }

    public boolean isAuthorizedFor(Transaction transaction) {
        return authorizedTransactions.contains(transaction.getType());
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
}
