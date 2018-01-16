package com.lashes.services;


import com.lashes.dao.CategoryDao;
import com.lashes.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryDao categoryDao;

    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    public List<Category> getAllCategories(String categoryType) {
        return categoryDao.getAllCategories(categoryType);
    }

    @Override
    public Category getSingleCategory(Long id) {
        return categoryDao.getSingleCategory(id);
    }

    @Override
    public void editCategory(Category category) {
        categoryDao.editCategory(category);
    }
}
