package com.eCommerce.eCommerce_API.services;


import com.eCommerce.eCommerce_API.model.Category;

import java.util.List;


public interface CategoryService {
    List<Category> getCategories();
    String createCategory(Category category);
    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
