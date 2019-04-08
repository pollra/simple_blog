package com.pollra.intercepter.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 반드시 로그인이 필요한 페이지에서만 구동되는 인터셉터
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("[login] login / start.");
        request.setAttribute("ip",request.getHeader("x-real-ip"));
        log.info("[login] Access IP: "+ request.getAttribute("ip"));
//        HttpSession session = request.getSession();
//        try {
//            String loginUser = session.getAttribute("lu").toString();
//        }catch (NullPointerException e){
//            log.info("[login] login info is null. redirect loginPage");
//            session.setAttribute("lu","");
//            response.sendRedirect("/login/page");
//        }

        log.info("[login] login / end.");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
