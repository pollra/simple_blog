package com.pollra.http.comment.controller;

import com.pollra.http.comment.exceptions.exception.CommentServiceException;
import com.pollra.http.comment.exceptions.exception.DataEntryException;
import com.pollra.http.comment.exceptions.exception.NotFoundException;
import com.pollra.http.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/comment", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
public class CommentRestController {
    private static final Logger log = LoggerFactory.getLogger(CommentRestController.class);

    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("select/{option}/value/{value}")
    public ResponseEntity<?> getCommentList(@PathVariable String option, @PathVariable int value) throws CommentServiceException {
        switch (option){
            case "list":
                return new ResponseEntity<>(commentService.getCommentList(value), HttpStatus.OK);
            case "one":
                return new ResponseEntity<>(commentService.getComment(value), HttpStatus.OK);
            default:
                throw new NotFoundException("입력된 옵션을 찾을 수 없습니다.");
        }
    }

    @PostMapping("create/one")
    public ResponseEntity<?> createOneComment(@RequestBody Map<String, Object> param, HttpServletRequest request){
        commentService.insertOneComment(param);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    @PutMapping("update/one")
    public ResponseEntity<?> updateOneComment(@RequestBody Map<String, Object> param, HttpServletRequest request){
        commentService.updateOneComment(param);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @PutMapping("delete/one")
    public ResponseEntity<?> deleteOneComment(@RequestBody Map<String, Object> param, HttpServletRequest request){
        commentService.deleteOneComment(param);
        ModelAndView view = new ModelAndView("1");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
