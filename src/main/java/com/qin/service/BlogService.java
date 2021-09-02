package com.qin.service;

import com.qin.dao.BlogDao;
import com.qin.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    public int insertBlog(Blog blog){
        return blogDao.insertBlog(blog);
    }
}
