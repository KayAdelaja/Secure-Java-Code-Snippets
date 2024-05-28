public class SecureAuthorization {
    public void checkUserAccess(User user, Resource resource) {
        if (!user.hasPermission(resource)) {
            throw new SecurityException("User does not have access to the resource.");
        }
        // Proceed with accessing the resource
    }
}

class User {
    private String username;
    private Set<String> permissions;

    public User(String username, Set<String> permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public boolean hasPermission(Resource resource) {
        return permissions.contains(resource.getRequiredPermission());
    }
}

class Resource {
    private String resourceName;
    private String requiredPermission;

    public Resource(String resourceName, String requiredPermission) {
        this.resourceName = resourceName;
        this.requiredPermission = requiredPermission;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }
}
