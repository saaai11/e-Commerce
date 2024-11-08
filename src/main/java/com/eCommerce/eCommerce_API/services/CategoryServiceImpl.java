package com.eCommerce.eCommerce_API.services;

import com.eCommerce.eCommerce_API.model.Category;
import com.eCommerce.eCommerce_API.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final List<Category> categories = new ArrayList<>();

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(Category category) {
        categoryRepository.save(category);
        System.out.println("hell");
        return "Categories added";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, categoryId + "not found"));
       categoryRepository.delete(category);
        return "Category deleted successfully : " + categoryId;
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category categoryLatest = categoryRepository.findById(categoryId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, categoryId + "not found"));

        category.setCategoryId(categoryId);
        categoryLatest = categoryRepository.save(category);
return categoryLatest;
    }
}
