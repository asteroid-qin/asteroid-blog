package com.qin.service;

import com.alibaba.druid.util.StringUtils;
import com.qin.dao.UserDao;
import com.qin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer id){
        return userDao.getUserById(id);
    }

    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }

    public int insertUser(User user){
        return userDao.insertUser(user);
    }


    /**
     * 传入request，判断user是否合法
     * 不合法传入""
     * @param request
     * @return
     */
    public String getUser(HttpServletRequest request){
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

        User user = getUserByName(name);
        if(null == user){
            return "";
        }

        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(digitId)
                || !StringUtils.equals(digitId, DigestUtils.md5DigestAsHex(user.getId().toString().getBytes())) ){
            return "";
        }

        return name;
    }

    /**
     * 注册或者登录账户
     * @param user
     * @param name
     * @param pwd
     * @param response
     */
    public void registeOrLoginUser(User user,String name,String pwd, HttpServletResponse response){
        // 准备放入编码后的id
        String digit;

        // 存在需要比对密码
        if(null != user){
            // 登录成功,设置用户名id和名字
            digit = DigestUtils.md5DigestAsHex(user.getId().toString().getBytes());
        }else{
            // 不存在则直接创建
            User u2 = new User(name, pwd);
            insertUser(u2);
            digit = DigestUtils.md5DigestAsHex(u2.getId().toString().getBytes());
        }

        // 把加密后的id和用户名放入cookie中
        Cookie c1 = new Cookie("digit", digit);
        Cookie c2 = new Cookie("name", name);

        response.addCookie(c1);
        response.addCookie(c2);
    }

    /**
     * 删除response里面的name和digit
     * @param response
     */
    public void exitUser(HttpServletResponse response){
        // 删除cookie
        response.addCookie(new Cookie("name",""));
        response.addCookie(new Cookie("digit",""));
    };
}
