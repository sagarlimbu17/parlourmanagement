package com.lashes.dao;

import com.lashes.entities.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

    @PersistenceContext
    EntityManager em;


    public void addCategory(Category category) {
        em.persist(category);
    }

    public List<Category> getAllCategories(String categoryType)  {
      List<Category> categories = em.createQuery("select c from Category c where c.categoryType=:categoryType")
              .setParameter("categoryType",categoryType)
              .getResultList();
       return categories;
    }

    @Override
    public Category getSingleCategory(Long id) {
        Category category = em.find(Category.class,id);
        return category;
    }

    @Override
    public void editCategory(Category category) {
        Category category1 = em.find(Category.class,category.getId());
        category1.setCategory(category.getCategory());
        em.merge(category1);
    }
}
