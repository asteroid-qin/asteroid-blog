package com.qin.controller;

import com.qin.entity.Category;
import com.qin.entity.R;
import com.qin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    @ResponseBody
    public R getCategory(){
        // 查询所有的分类并返回
        List<Category> categories = categoryService.getCategories();

        return R.ok(categories);
    }
}
