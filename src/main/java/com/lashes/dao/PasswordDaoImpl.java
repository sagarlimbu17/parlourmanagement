package com.lashes.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;


@Repository
@Transactional
public class PasswordDaoImpl implements PasswordDao {

    @PersistenceContext
    EntityManager em;

    public String password(String username) {
        String password=null;
        Query query = em.createQuery("select u.password from User u where u.username=:username")
                .setParameter("username",username);
        List<Object> passwordList = query.getResultList();

        for(Object pass : passwordList){
            password = pass.toString();
        }
        return password;
    }

    public void changePassword(String usern, String pass) {
        Query query = em.createQuery("update User u set u.password=:password where u.username=:username");
        query.setParameter("username",usern);
        query.setParameter("password",pass);
        query.executeUpdate();

    }
}
