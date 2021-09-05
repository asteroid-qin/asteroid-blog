package com.qin;

import com.qin.dao.UserDao;
import com.qin.entity.Blog;
import com.qin.entity.User;
import com.qin.service.BlogService;
import com.qin.service.CommentService;
import com.qin.vo.CommentVO;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@ContextConfiguration(locations = "classpath:spring-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BlogService blogService;
    @Autowired
    private MutableDataSet options;

    @Autowired
    private CommentService commentService;

    @Test
    public void test4(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm  Z");
        User user = userDao.getUserById(1);
        Date time = user.getCreateTime();
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println( dateFormat.format(time));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        System.out.println( dateFormat.format(time));
    }

    @Test
    public void test03(){
        for (CommentVO commentVO : commentService.getCommentByBlogId(13)) {
            System.out.println(commentVO);
        }

    }


    @Test
    public void test01() throws SQLException {
        User s1 = userDao.getUserByName("张三");
        User s2 = userDao.getUserByName("秦爽");
        System.out.println(s1);
        System.out.println("-------------");
        System.out.println(s2);
    }

    @Test
    public void test02(){
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        List<Blog> blogs = blogService.getPageBlogsByAllCategory(0, 10);
        // 转成html

        blogs.forEach(blog-> {
            Node document = parser.parse(blog.getContent());
            String html = renderer.render(document);
            System.out.println(html.replaceAll("<[^>]*>",""));
        });
    }


}
