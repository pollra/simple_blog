package com.pollra.intercepter.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component
public class AllPageInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AllPageInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[inter] all / start.");
//        log.info("[all] Access IP: "+ request.getRemoteAddr());
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            log.info(name + " : " +value);
        }
        log.info("[all]");
        HttpSession session = request.getSession();
        try {
            String loginUser = session.getAttribute("lu").toString();
        }catch (NullPointerException e){
            session.setAttribute("lu","");
        }
        log.info("[inter] all / end.");
        return super.preHandle(request, response, handler);
    }
}
