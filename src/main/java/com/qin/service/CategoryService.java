package com.qin.service;

import com.qin.dao.CategoryDao;
import com.qin.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }
}
