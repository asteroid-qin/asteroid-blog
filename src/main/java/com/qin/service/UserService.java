package com.qin.service;

import com.alibaba.druid.util.StringUtils;
import com.qin.dao.UserDao;
import com.qin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

}
