package com.qin.service;

import com.alibaba.druid.util.StringUtils;
import com.qin.dao.BlogDao;
import com.qin.entity.Blog;
import com.qin.entity.Category;
import com.qin.entity.User;
import com.qin.vo.BlogVO;
import com.qin.vo.BlogVOWIndex;
import com.qin.vo.CommentVO;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private MutableDataSet options;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;

    // 准备解析html
    private final Parser parser = Parser.builder(options).build();
    private final HtmlRenderer renderer = HtmlRenderer.builder(options).build();

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

    /**
     * 通过博客id查询博客添加额外的内容并封装，返回给blog页
     * @param id 博客的id
     * @return
     */
    public BlogVOWIndex getBlogPageBlog(Integer id){
        Blog _blog = getBlogById(id);
        BlogVOWIndex blog = new BlogVOWIndex(_blog);

        // 为blog设置用户名和分类名
        User user = userService.getUserById(blog.getAuthorId());
        blog.setAuthorName(user.getName());

        Category category = categoryService.getCategoryById(_blog.getCategoryId());
        blog.setCategoryName(category.getName());

        return blog;
    }

    /**
     * 返回首页页面的所有封装好的博客
     * @param categoryId 分类id，-1查所有
     * @param start
     * @param size
     * @return
     */
    public List<BlogVO> getIndexPageBlogVOs(Integer categoryId, Integer start, Integer size){
        List<Blog> blogs;
        // 开始分页查询
        if(categoryId == -1){
            blogs = getPageBlogsByAllCategory(start, size);
        }else{
            blogs = getPageBlogsByCategory(start, categoryId, size);
        }

        // 包装blog
        return getBlogVOS(blogs);
    }


    public List<BlogVO> getHomePageBlogVOs(Integer userId){
        List<Blog> blogs = getBLogsByUserId(userId);

        // 将blog转成blogvo并返回
        return getBlogVOS(blogs);
    }

    /**
     * 将传入的blog进行封装
     * @param blogs
     * @return
     */
    private List<BlogVO> getBlogVOS(List<Blog> blogs) {
        return blogs.stream().map(blog -> {
            BlogVO vo = new BlogVO(blog);
            // 查询作者名字
            User user = userService.getUserById(blog.getAuthorId());
            vo.setAuthorName(user.getName());
            // 设置描述
            vo.setDescription(getDescription(blog.getContent()));
            // 设置评论数
            List<CommentVO> comments = commentService.getCommentByBlogId(blog.getId());
            // 需要计算一级评论及其二级评论的总和
            vo.setCommentCount(comments.size() + comments.stream().mapToInt(cm->cm.getChildren().size()).sum());

            return vo;
        }).collect(Collectors.toList());
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

    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    public int getCountBlogById(Integer id){
        return blogDao.getCountBlogById(id);
    }

    public List<Blog> getBlogs(){
        return blogDao.getBlogs();
    }

    /**
     * 根据用户id查出以时间大小逆序的所有博客
     * @param userId
     * @return
     */
    public List<Blog> getBLogsByUserId(Integer userId){
        return blogDao.getBLogsByUserId(userId);
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

    /**
     * 从request中取到user，并返回这个用户的所有博客
     * @param request
     * @return
     */
    public List<BlogVO> getHomePageBlogVOsByReq(HttpServletRequest request) {
        // 拿到用户名
        String name = userService.getUser(request);

        List<BlogVO> blogs = new ArrayList<>();
        if(!StringUtils.isEmpty(name)){
            User user = userService.getUserByName(name);

            blogs = getHomePageBlogVOs(user.getId());
        }

        return blogs;
    }
}
