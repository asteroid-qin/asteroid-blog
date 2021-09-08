package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.Blog;
import com.qin.entity.R;
import com.qin.service.BlogService;
import com.qin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EditController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request){
        // 如果用户名不存在，则重定向到首页
        String user = userService.getUser(request);
        if(!StringUtils.isEmpty(user)){
            return "edit";
        }

        // 默认重定向,回到首页
        return "redirect:/";
    }



    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    @ResponseBody
    public R insert(@RequestParam("title")String title,
                    @RequestParam("category_id")String categoryId,
                    @RequestParam("content")String content,
                    HttpServletRequest request){
        String name = userService.getUser(request);
        if(StringUtils.isEmpty(name)){
            return R.no("用户不合法！", null);
        }

        if(!StringUtils.isNumber(categoryId)){
            return R.no("分类名不合法！", null);
        }

        // 确认完成后，开始往数据库中添加blog
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setAuthorId(userService.getUserByName(name).getId());
        blog.setCategoryId(Integer.parseInt(categoryId));
        blog.setContent(content);

        blogService.insertBlog(blog);

        return R.ok("恭喜你"+ name +"，博客发布成功！", null);
    }

    @RequestMapping("blog/count")
    @ResponseBody
    public R blogcount(@RequestParam("categoryId") String _categoryId){
        if(!StringUtils.isNumber(_categoryId)){
            return R.no("查询到的分类id错误！", null);
        }

        Integer categoryId = Integer.parseInt(_categoryId);
        int count;

        if(categoryId == -1){
            count = blogService.getBlogsByALLCategory();
        }else{
            count = blogService.getBlogsByCategory(categoryId);
        }

        return R.ok(count);
    }
    

}
