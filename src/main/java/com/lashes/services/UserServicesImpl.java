package com.lashes.services;


import com.lashes.dao.UserDao;
import com.lashes.entities.User;
import com.lashes.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean createNewUser(User user) {
        return userDao.createUser(user);

    }

    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    @Override
    public boolean editUser(User user) {
        return userDao.editUser(user);
    }

    @Override
    public UserRole getUserRole(Long id) {
        return userDao.getUserRole(id);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> getInactiveUser() {
        return userDao.getInactiveUsers();
    }

    @Override
    public void activateUser(Long id) {
        userDao.activateUser(id);
    }
}
