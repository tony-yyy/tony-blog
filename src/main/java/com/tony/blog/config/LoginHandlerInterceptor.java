package com.tony.blog.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
//        session.setAttribute("USER_ID", 1L);
        //获取session中的用户名
        Object loginUser = request.getSession().getAttribute("USER_ID");
//        if (true) return true;
        if (loginUser == null){
            //为空就返回登录
            response.sendRedirect("/");
            return false;
        }else{
            return true;
        }
    }
}
