package com.lashes.services;

import com.lashes.entities.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryService {
    void addCategory(Category category);
    List<Category> getAllCategories(String categoryType);
    Category getSingleCategory(Long id);
    void editCategory(Category category);
}
