package com.lashes.dao;

import com.lashes.entities.User;
import com.lashes.entities.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    public User findByUsername(String username) {

                /*List<User> users = em.createQuery("select c from User c " +
                        "join fetch c.userRole where c.username=:usernam")*/
        List<User> users = em.createQuery("select c from User c where c.username=:username ")
                .setParameter("username",username)
                .getResultList();
        if(users.size()>0){
            return users.get(0);
        }
        else{
            return  null;
        }
    }

    @Override
    public boolean createUser(User user) {
        em.persist(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("select u from User u where u.enabled=true");
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public User findUser(Long id) {
        User user = em.find(User.class,id);
        return user;
    }

    @Override
    public boolean editUser(User user) {
        em.merge(user);
        return true;
    }

    @Override
    public UserRole getUserRole(Long id) {
        Query query = em.createNativeQuery("select * from user_roles u where u.user_id =:user_id",UserRole.class)
                .setParameter("user_id",id);

        List<UserRole> userRoles = query.getResultList();
        if(!userRoles.isEmpty()){
            return userRoles.get(0);
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        User user = em.find(User.class,id);
        user.setEnabled(false);
        editUser(user);
        return true;
    }

    @Override
    public List<User> getInactiveUsers() {
        Query query = em.createQuery("select u from User u where u.enabled=false");
        List<User> userList = query.getResultList();
        return userList;
    }

    @Override
    public void activateUser(Long id) {
        User user = em.find(User.class,id);
        user.setEnabled(true);
        em.merge(user);
    }
}
