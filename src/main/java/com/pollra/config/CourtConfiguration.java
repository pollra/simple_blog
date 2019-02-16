package com.pollra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "com.pollra.*")   // 서블릿이 스캔할 Bean들의 하위경로
public class CourtConfiguration {

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver internalResourceViewResolver
                = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/templates/");  // view 상대경로
        internalResourceViewResolver.setSuffix(".html");    // 확장자
        return internalResourceViewResolver;
    }
}
