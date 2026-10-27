package com.example.dinedesk;

public class UserAccount {
    public String username;
    public String password;
    public String role;
    public UserAccount(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}