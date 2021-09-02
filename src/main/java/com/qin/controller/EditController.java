package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.Blog;
import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.BlogService;
import com.qin.service.UserService;
import com.sun.istack.internal.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;

@Controller
public class EditController {

    final Logger logger = Logger.getLogger(EditController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request){
        // 如果用户名不存在，则重定向到首页
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            String key = cookie.getName();
            if("name".equals(key) && !StringUtils.isEmpty(cookie.getValue())){
                // 找到就去页面
                logger.log(Level.INFO,"用户"+ key+"来到edit页面");
                return "edit";
            }
        }

        // 默认重定向,回到首页
        return "redirect:/";
    }

    @RequestMapping(value = "/blog", method = RequestMethod.PUT)
    @ResponseBody
    public R insert(@RequestParam("title")String title,
                    @RequestParam("category_id")String categoryId,
                    @RequestParam("content")String content,
                    HttpServletRequest request){
        // 取出当前id，查询用户后判断两个id是否相等
        Cookie[] cookies = request.getCookies();
        String name = "", digitId = "";
        for (Cookie cookie: cookies) {
            if("digit".equals(cookie.getName())){
                digitId = cookie.getValue();
            }else if("name".equals(cookie.getName())){
                name = cookie.getValue();
            }
        }
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(digitId) || !StringUtils.isNumber(categoryId)){
            // 没有用户名或者加密id，失败
            return R.no("cookie中查无此人！", null);
        }

        User user = userService.getUserByName(name);
        if(user == null){
            return R.no("数据库中查无此人！", null);
        }
        if(!digitId.equals(DigestUtils.md5DigestAsHex(user.getId().toString().getBytes()))){
            return R.no("认证id错误！！", null);
        }

        // 确认完成后，开始往数据库中添加blog
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setAuthorId(user.getId());
        blog.setCategoryId(Integer.parseInt(categoryId));
        blog.setContent(content);
        blogService.insertBlog(blog);

        logger.log(Level.INFO, "用户"+ name +"的博客已经插入成功！");
        return R.ok("恭喜你"+ name +"，博客发布成功！", null);
    }

}
