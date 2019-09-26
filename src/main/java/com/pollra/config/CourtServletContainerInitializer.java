package com.pollra.config;

import org.apache.tomcat.util.descriptor.web.SessionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.Collections;
import java.util.Set;

public class CourtServletContainerInitializer implements ServletContainerInitializer {
    @Autowired
    private SessionConfig sessionConfig;

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

        ctx.setSessionTrackingModes(
                Collections.singleton(SessionTrackingMode.COOKIE)
        );
        SessionCookieConfig sessionCookieConfig =
                ctx.getSessionCookieConfig();
        sessionCookieConfig.setHttpOnly(false);
    }
}
