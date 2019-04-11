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
import java.util.List;
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
    public ResponseEntity<?> createOneBoard(@RequestBody Map<String, Object> param){
        int resultBoardNum = boardService.createBoard(param);
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
    @PutMapping("/posts/{targetNum}/update/{option}/value/{value}")
    public ResponseEntity<?> updateValueBoard(
            @PathVariable int targetNum,
            @PathVariable String option,
            @PathVariable int value){
        boardService.updateValueBoard(targetNum, option, value);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * 업데이트 할 항목들을 모두 작성 완료 후 실행됨
     * 데이터베이스를 수정하기때문에 수정된 값이 들어옴.
     * 실행 후
     */
    @PutMapping("/posts/{targetNum}/update/{option}")
    public ResponseEntity<?> updateBodyBoard(@PathVariable int targetNum, @PathVariable String option, @RequestBody Map<String, Object> param){
        boardService.updateBodyBoard(targetNum, option, param);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    /**
     * 카테고리 기준 제일 마지막 포스트의 번호를 리턴
     */
    @GetMapping("/category/{categoryName}/type/{option}")
    public ResponseEntity<?> selectLastPost(@PathVariable String categoryName, @PathVariable String option){
        int result = 0;
        if(option.equals("int")){
            result = boardService.selectLastPostToCategoryNum(Integer.parseInt(categoryName));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/posts/category/{num}/list")
    public ResponseEntity<?> selectPostList(@PathVariable int num){
        List<BoardVO> list = boardListService.getBoardListToCategory(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}