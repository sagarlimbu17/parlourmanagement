package com.lashes.dao;

import com.lashes.entities.User;
import com.lashes.entities.UserRole;

import java.util.List;

public interface UserDao {

    User findByUsername(String username);
    boolean createUser(User user);
    List<User> getAllUsers();
    User findUser(Long id);
    boolean editUser(User user);
    UserRole getUserRole(Long id);
    boolean deleteUser(Long id);
    List<User> getInactiveUsers();
    void activateUser(Long id);
}
