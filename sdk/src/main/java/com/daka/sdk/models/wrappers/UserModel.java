package com.daka.sdk.models.wrappers;

/**
 * Created by Dana on 30-Sep-17.
 */

public class UserModel {
    String username;
    String password;

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
