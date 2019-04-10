package com.pollra.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.Collections;
import java.util.Set;

public class CourtServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(CourtConfiguration.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic courtRegistration =
                ctx.addServlet("court", dispatcherServlet);
        courtRegistration.setLoadOnStartup(1);
        courtRegistration.addMapping("/");

        // 쿠키 설정
        ctx.setSessionTrackingModes(
                Collections.singleton(SessionTrackingMode.COOKIE)
        );
        SessionCookieConfig sessionCookieConfig =
                ctx.getSessionCookieConfig();
        sessionCookieConfig.setHttpOnly(true);

    }
}
