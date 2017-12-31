package com.lashes.services;

import com.lashes.entities.User;
import com.lashes.entities.UserRole;

import java.util.List;

public interface UserServices {

    boolean createNewUser(User user);
    List<User> getAllUsers();
    User findUser(Long id);
    boolean editUser(User user);
    UserRole getUserRole(Long id);
    boolean deleteUser(Long id);
    List<User> getInactiveUser();
    void activateUser(Long id);
}
