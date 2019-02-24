package com.pollra.http.login.controller;

import com.pollra.http.login.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * Exception processing{@link com.pollra.http.login.exceptions.handler.ApiExceptionHandler}
 *
 */
@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping        // 유저 회원가입
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> param){
        userService.createUser(param);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PutMapping         // 유저 정보 업데이트
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> param, HttpServletRequest request){
        userService.updateUser(param, request);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping      // 회원 탈퇴
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Object> param){
        userService.deleteUser(param);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
