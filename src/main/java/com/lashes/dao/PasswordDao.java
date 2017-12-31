package com.lashes.dao;

public interface PasswordDao {

    String password(String username);
    void changePassword(String username, String password);
}
