package com.example.demo.interceptor;

import com.example.demo.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user==null){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }
        return true;
    }

}