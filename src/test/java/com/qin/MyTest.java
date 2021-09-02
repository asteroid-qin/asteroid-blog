package com.qin;

import com.qin.dao.UserDao;
import com.qin.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@ContextConfiguration(locations = "classpath:spring-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void test01() throws SQLException {
        User s1 = userDao.getUserByName("张三");
        User s2 = userDao.getUserByName("秦爽");
        System.out.println(s1);
        System.out.println("-------------");
        System.out.println(s2);
    }
}
