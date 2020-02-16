package com.example.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SessionInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List list = new ArrayList();
        list.add("/");
        list.add("/index");
        list.add("/loginuser");
        list.add("/signupuser");
        list.add("/static/**");
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns(list);
    }
}
