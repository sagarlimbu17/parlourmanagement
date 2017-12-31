package com.lashes.services;

import com.lashes.entities.RgProduct;

public interface PasswordService {

    String password(String username);
    void changePassword(String username,String password);


}
