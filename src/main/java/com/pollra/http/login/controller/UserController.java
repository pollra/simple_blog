package com.pollra.http.login.controller;

import com.pollra.http.login.exceptions.exception.DataEntryException;
import com.pollra.http.login.exceptions.exception.UserNotFoundException;
import com.pollra.http.login.exceptions.exception.UserServiceException;
import com.pollra.http.login.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Exception processing{@link com.pollra.http.login.exceptions.handler.ApiExceptionHandler}
 *
 */
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> getLoginCondition(HttpServletRequest request){
        String loginUser = userService.loginCheck(request);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> setLoginAction(@RequestBody Map<String, Object> param, HttpServletRequest request){
        userService.loginUser(param, request);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> param){
        userService.createUser(param);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> param){
        userService.updateUser(param);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Object> param){
        userService.deleteUser(param);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
