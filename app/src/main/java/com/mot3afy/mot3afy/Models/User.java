package com.mot3afy.mot3afy.Models;

/**
 * Created by Hind on 10/18/2017.
 */

public class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
