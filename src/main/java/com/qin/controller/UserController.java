package com.qin.controller;

import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public R registerUser(@RequestParam("name")String name,
                          @RequestParam("password") String pwd,
                          HttpServletResponse response){
        // 根据用户名查询这个用户
        // 如果用户存在，比对密码
        User user = userService.getUserByName(name);

        // 如果用户存在且密码错误
        if(null != user && user.getPassword().equals(pwd)){
            // 登录失败，需要提示信息
            return R.no("登录失败！密码错误！", null);
        }

        // 准备放入编码后的id
        String digit;

        // 存在需要比对密码
        if(null != user){
            // 登录成功,设置用户名id和名字
            digit = DigestUtils.md5DigestAsHex(user.getId().toString().getBytes());
        }else{
            // 不存在则直接创建
            User u2 = new User(name, pwd);
            userService.insertUser(u2);

            digit = DigestUtils.md5DigestAsHex(u2.getId().toString().getBytes());
        }

        // 把加密后的id和用户名放入cookie中
        Cookie c1 = new Cookie("digit", digit);
        Cookie c2 = new Cookie("name", name);

        response.addCookie(c1);
        response.addCookie(c2);

        return R.ok("登录成功");
    }

    @RequestMapping("/exit")
    public String exit(HttpServletResponse response){
        // 删除cookie
        response.addCookie(new Cookie("name",""));
        response.addCookie(new Cookie("digit",""));
        return "redirect:/index";
    }
}
