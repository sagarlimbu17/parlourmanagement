package com.lashes.dao;

import com.lashes.entities.Category;

import java.util.List;

public interface CategoryDao {

    void addCategory(Category category);
    List<Category> getAllCategories(String categoryType);
}
