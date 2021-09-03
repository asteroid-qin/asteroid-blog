package com.qin;

import com.qin.dao.UserDao;
import com.qin.entity.Blog;
import com.qin.entity.User;
import com.qin.service.BlogService;
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
import java.util.List;

@ContextConfiguration(locations = "classpath:spring-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BlogService blogService;
    @Autowired
    private MutableDataSet options;
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
