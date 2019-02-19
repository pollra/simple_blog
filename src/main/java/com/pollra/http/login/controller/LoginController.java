package com.pollra.http.login.controller;

import com.pollra.http.login.service.UserService;
import com.pollra.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * Exception processing{@link com.pollra.http.login.exceptions.handler.ApiExceptionHandler}
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping   // 로그인 상태 체크
    public ResponseEntity<?> getLoginCondition(HttpServletRequest request){
        String loginUser = userService.loginCheck(request);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @PostMapping  // 로그인 시도
    public ResponseEntity<?> setLoginAction(@RequestBody Map<String, Object> param, HttpServletRequest request){
        userService.loginUser(param, request);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("page")
    public ModelAndView goLoginPage(){
        ModelAndView view = new ModelAndView("login");
        return view;
    }
}
