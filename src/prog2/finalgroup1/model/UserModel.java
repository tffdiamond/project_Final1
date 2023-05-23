package prog2.finalgroup1.model;

public class UserModel {
    private String username;
    private String password;

    /**
     *
     * @param username
     * @param password
     */
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @param user
     */
    public UserModel(UserModel user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
