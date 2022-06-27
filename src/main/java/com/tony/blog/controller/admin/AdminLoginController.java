package com.tony.blog.controller.admin;

import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.pojo.User;
import com.tony.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public ResultInfo login(User loginUser, HttpSession session){
        if (loginUser == null) return new ResultInfo(true, "账号或密码错误！");
        User user = userService.checkUser(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            session.setAttribute("USER_ID",user.getId());
            session.setMaxInactiveInterval(-1);     // 设置session永不过期
            return new ResultInfo("登录成功", false, "");
        } else {
            return new ResultInfo(true, "账号或密码错误！");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("USER_ID");
        return "redirect:/admin/login.html";
    }
}
