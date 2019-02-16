package com.pollra.test.boardTest;

import com.pollra.http.gbook.domain.GBookVO;
import com.pollra.http.gbook.exceptions.GBookServiceException;
import com.pollra.http.gbook.service.GBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test/board")
public class BoardTestController {
    private GBookService GBookService;

    public BoardTestController(GBookService GBookService) {
        this.GBookService = GBookService;
    }

    @GetMapping("get/{num}")
    public ResponseEntity<GBookVO> getOneBoard(@PathVariable("num") int num){
        GBookVO resultBoard = null;
        try {
            resultBoard = GBookService.selectOneGBook(num);
        } catch (GBookServiceException e) {
            e.printStackTrace();
        }
        if(resultBoard == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultBoard, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteOneBoard(HttpServletRequest request){
        return new ResponseEntity<>("deleteFail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
