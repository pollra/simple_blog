package com.pollra.http.board.controller;

import com.pollra.http.board.domain.BoardVO;
import com.pollra.http.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/posts")
    public String postsPage(){
        return "post";
    }

    @GetMapping("/posts/{num}")
    public String postsPage(@PathVariable int num){
        return "postView";
    }

    @GetMapping("/posts/category/{categoryNum}")
    public String selectSetCategory(@PathVariable int categoryNum){
        log.info("/posts/category/{categoryNum} 실행된당 ㅇㅅ <r~*");
        return "post";
    }

    @GetMapping("/posts/update/{num}")
    public String postsUpdatePage(@PathVariable int num){
        return "postsUpdate";
    }

    @GetMapping("/postnotfound")
    public String postNotFound(){
        return "postNotFound";
    }
}
