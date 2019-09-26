package com.pollra.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor loginInterceptor;

    @Autowired
    @Qualifier(value = "allPageInterceptor")
    private HandlerInterceptor allPageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        registry.addInterceptor(loginInterceptor).pathMatcher(pathMatcher)
                .addPathPatterns("/user/**",
                        "/posts",
                        "/posts/category/**",
                        "/posts/update/**")  // Using interceptor
                .excludePathPatterns(); // Exclude interceptor
        registry.addInterceptor(allPageInterceptor).pathMatcher(pathMatcher)
                .excludePathPatterns("/user/**",
                        "/posts",
                        "/posts/category/**",
                        "/posts/update/**").addPathPatterns("/**");
    }
}
