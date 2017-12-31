package com.lashes.services;

import com.lashes.dao.PasswordDao;
import com.lashes.entities.RgProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordDao passwordDao;

    public String password(String username) {
        return passwordDao.password(username);
    }

    public void changePassword(String username, String password) {
         passwordDao.changePassword(username,password);
    }
}
