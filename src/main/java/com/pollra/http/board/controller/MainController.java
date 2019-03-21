package com.pollra.http.board.controller;

import com.pollra.http.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private BoardService boardService;

    public MainController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String goMainPage(){
        int result = boardService.selectLastPostToAllCategories();
        if(result <= 0){
            return "redirect:/postnotfound";
        }
        return "redirect:/posts/"+result;
    }
}
