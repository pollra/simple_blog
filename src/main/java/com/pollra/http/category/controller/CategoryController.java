package com.pollra.http.category.controller;

import com.pollra.http.board.service.BoardService;
import com.pollra.http.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;
    private BoardService boardService;

    public CategoryController(CategoryService categoryService, BoardService boardService) {
        this.categoryService = categoryService;
        this.boardService = boardService;
    }

    @GetMapping("/category/{num}/lastpost")
    public String locationLastPost(@PathVariable int num){
        int lastPost = boardService.selectLastPostToCategoryNum(num);

        if(lastPost != 0){
            return "redirect:/posts/"+lastPost;
        }
        return "redirect:/postnotfound";
    }
}
