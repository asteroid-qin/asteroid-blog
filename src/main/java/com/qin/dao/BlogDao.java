package com.qin.dao;

import com.qin.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogDao {

    int insertBlog(Blog blog);

    List<Blog> getPageBlogsByAllCategoryNew(@Param("start") Integer start, @Param("size") Integer size);

    List<Blog> getPageBlogsByCategoryNew(@Param("start") Integer start,@Param("categoryId")Integer categoryId, @Param("size") Integer size);

    int getBlogsByALLCategory();

    List<Blog> getBlogs();

    List<Blog> getBLogsByUserId(Integer userId);

    int getBlogsByCategory(Integer categoryId);

    Blog getBlogById(Integer id);

    int getCountBlogById(Integer id);
}
