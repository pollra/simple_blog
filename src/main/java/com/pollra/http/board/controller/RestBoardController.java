package com.pollra.http.board.controller;

import com.pollra.http.board.domain.BoardVO;
import com.pollra.http.board.service.BoardListService;
import com.pollra.http.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RestBoardController {
    private static final Logger log = LoggerFactory.getLogger(RestBoardController.class);

    private BoardService boardService;
    private BoardListService boardListService;

    public RestBoardController(BoardService boardService, BoardListService boardListService) {
        this.boardService = boardService;
        this.boardListService = boardListService;
    }

    @PostMapping("/posts/create")
    public ResponseEntity<?> createOneBoard(@RequestBody Map<String, Object> param, HttpServletRequest request){
        int resultBoardNum = boardService.createBoard(param, request);
        return new ResponseEntity<>(resultBoardNum, HttpStatus.OK);
    }

    @GetMapping("/posts/select/{num}")
    public ResponseEntity<?> selectOneBoard(@PathVariable int num){
        BoardVO boardVO = boardService.selectBoard(num);
        return new ResponseEntity<>(boardVO, HttpStatus.OK);
    }

    @GetMapping("/posts/boardcategory")
    public ResponseEntity<?> selectAllBoardCategory(){
        return new ResponseEntity<>(boardListService.getBoardCategoryList(), HttpStatus.OK);
    }

    @PutMapping("/posts/update/{option}")
    public ResponseEntity<?> updateVisibleBoard(@PathVariable String option, @RequestBody Map<String, Object> param){
        boardService.updateBoard(option, param);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}