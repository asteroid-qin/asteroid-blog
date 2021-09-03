package com.qin.service;

import com.qin.dao.BlogDao;
import com.qin.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    public int insertBlog(Blog blog){
        return blogDao.insertBlog(blog);
    }

    /**
     * 查所有分类的blog，通过分页方式，按照时间降序
     * @param start
     * @param size
     * @return
     */
    public List<Blog> getPageBlogsByAllCategory(Integer start, Integer size){
        return blogDao.getPageBlogsByAllCategoryNew(start, size);
    }

    public List<Blog> getPageBlogsByCategory(Integer start, Integer categoryId,Integer size){
        return blogDao.getPageBlogsByCategoryNew(start, categoryId, size);
    }

    public int getBlogsByALLCategory(){
        return blogDao.getBlogsByALLCategory();
    }

    public int getBlogsByCategory(Integer categoryId){
        return blogDao.getBlogsByCategory(categoryId);
    }
}
