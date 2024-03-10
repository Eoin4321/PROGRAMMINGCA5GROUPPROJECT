package OOB.example;

public class User {
    private int id;
    private String username;
    private String password;
    private String displayName;
    private boolean isAdmin;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

