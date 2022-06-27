package com.tony.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminIndexController {
    @RequestMapping("/admin")
    public String index(HttpSession session){
        if (session.getAttribute("USER_ID") != null)
            return "redirect:/admin/index.html";
        return "redirect:/admin/login.html";
    }

}
