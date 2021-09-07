package com.qin.controller;

import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if(null != user && !user.getPassword().equals(pwd)){
            // 登录失败，需要提示信息
            return R.no("登录失败！密码错误！", null);
        }

        userService.registeOrLoginUser(user, name, pwd, response);

        return R.ok("登录成功");
    }

    @RequestMapping("/exit")
    public String exit(HttpServletResponse response){
        userService.exitUser(response);

        return "redirect:/";
    }


}
