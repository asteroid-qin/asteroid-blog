package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.Blog;
import com.qin.entity.Category;
import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.BlogService;
import com.qin.service.CategoryService;
import com.qin.service.UserService;
import com.qin.vo.BlogVO;
import com.sun.istack.internal.logging.Logger;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class EditController {

    final Logger logger = Logger.getLogger(EditController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MutableDataSet options;

    // 准备解析html
    private final Parser parser = Parser.builder(options).build();
    private final HtmlRenderer renderer = HtmlRenderer.builder(options).build();

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

    @RequestMapping("/blogs")
    @ResponseBody
    public R getBlog(@RequestParam("start")String _start,
                     @RequestParam("size")String _size,
                     @RequestParam("categoryId")String _categoryId){
        // 其中任何一个不是数字就返回no
        if(StringUtils.isNumber(_start) && StringUtils.isNumber(_size) && StringUtils.isNumber(_categoryId)){
            int start = Integer.parseInt(_start);
            int size = Integer.parseInt(_size);
            int categoryId = Integer.parseInt(_categoryId);

            List<Blog> blogs;
            // 开始分页查询
            if(categoryId == -1){
                blogs = blogService.getPageBlogsByAllCategory(start, size);
            }else{
                blogs = blogService.getPageBlogsByCategory(start, categoryId, size);
            }

            // 包装blog
            List<BlogVO> blogVo = blogs.stream().map(blog -> {
                BlogVO vo = new BlogVO(blog);
                // 查询作者名字
                User user = userService.getUserById(blog.getAuthorId());
                vo.setAuthorName(user.getName());
                // 设置描述
                vo.setDescription(getDescription(blog.getContent()));
                return vo;
            }).collect(Collectors.toList());

            return R.ok("日志查询成功！", blogVo);
        }

        return R.no("前端发送的数据错误！", null);
    }

    @RequestMapping(value = "/blog", method = RequestMethod.PUT)
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

        User user = userService.getUserByName(name);

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
    
    /**
     * 输入des（带makedown语法的），得到255以内的description
     * @param des 带makedown语法的原始字符串
     * @return res 去掉makedown语法的字符串
     */
    private String getDescription(String des){
        Node document = parser.parse(des);
        String html = renderer.render(document);
        String res = html.replaceAll("<[^>]*>", "");

        if(StringUtils.isEmpty(res)) return "";
        return res.substring(0, Math.min(res.length(), 255));
    }
}
