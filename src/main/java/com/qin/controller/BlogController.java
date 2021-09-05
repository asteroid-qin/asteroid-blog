package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.Blog;
import com.qin.entity.Category;
import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.BlogService;
import com.qin.service.CategoryService;
import com.qin.service.UserService;
import com.qin.vo.BlogVOWIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("blog/{id}")
    public String toBlog(@PathVariable("id") String _id){
        if(StringUtils.isNumber(_id)){
            int id = Integer.parseInt(_id);
            // 通过博客的id去查数据库，如果只有1条就正常跳转到blog页面
            int count = blogService.getCountBlogById(id);

            if(count == 1){
                return "blog";
            }
        }

        return "/";
    }

    @RequestMapping("blog")
    @ResponseBody
    public R getBlog(@RequestParam("id")String _id){
        if(StringUtils.isNumber(_id)){
            int id = Integer.parseInt(_id);
            // 通过博客的id去查数据库，如果只有1条就正常跳转到blog页面
            Blog _blog = blogService.getBlogsById(id);

            if(_blog != null){
                BlogVOWIndex blog = new BlogVOWIndex(_blog);

                // 为blog设置用户名和分类名
                User user = userService.getUserById(blog.getAuthorId());
                blog.setAuthorName(user.getName());

                Category category = categoryService.getCategoryById(_blog.getCategoryId());
                blog.setCategoryName(category.getName());

                return R.ok("已查到博客！", blog);
            }
        }

        return R.no(null);
    }
}
