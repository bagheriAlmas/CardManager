package ir.almasapps.almascardmanager.DB.Model;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class User {
    private int user_id;
    private String user_username;
    private String user_password;

    public User() {
    }

    public User(int user_id) {
        this.user_id = user_id;
    }

    public User(String user_username, String user_password) {
        this.user_username = user_username;
        this.user_password = user_password;
    }

    public User(int user_id, String user_username, String user_password) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

}
