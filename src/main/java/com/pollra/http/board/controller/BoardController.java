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
    public ModelAndView postsPage(){
        return new ModelAndView("post");
    }

    @GetMapping("/posts/{num}")
    public ModelAndView postsPage(@PathVariable int num){
        ModelAndView view = new ModelAndView("postView");
        return view;
    }

    @GetMapping("/posts/update/{num}")
    public ModelAndView postsUpdatePage(){
        return new ModelAndView("postsUpdate");
    }
}
