package com.pollra.http.gbook.controller;

import com.pollra.http.gbook.domain.GBookVO;
import com.pollra.http.gbook.exceptions.*;
import com.pollra.http.gbook.service.GBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gbook")
public class GBookRestController {
    private static final Logger log = LoggerFactory.getLogger(GBookRestController.class);
    private GBookService GBookService;
    public GBookRestController(GBookService GBookService) {
        this.GBookService = GBookService;
    }

    @PostMapping("")
    public ResponseEntity<String> inputData(@RequestBody Map<String, Object> param){
        try {
            GBookService.createGBook(param.get("content").toString());
            log.info("데이터 저장 완료.");
            return new ResponseEntity<>("작성완료", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.info("유저 정보가 올바르지 않음");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataEntryException e){
            log.info("ㅇㅅㅇ?"+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (GBookServiceException e) {
            log.info("ㅇㅂㅇ..."+e.getMessage());
            return new ResponseEntity<>("서버에 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getData(){
        try {
            List<GBookVO> resultList = GBookService.selectAllGBookList();
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (GBookNotFoundException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch (GBookServiceException e){
            log.info(e.getMessage());
            return new ResponseEntity<>("서버에 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * DataEntryException
     * GBookNotFoundException
     * InvalidRequestException
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteData(@RequestBody Map<String, Object> param){
        // 데이터를 삭제하려면?
        // 삭제하기위한 데이터의 넘버를 받아야함
        try {
            GBookService.deleteOneGBook(
                    Integer.parseInt(param.get("gbookNum").toString())
            );
            return new ResponseEntity<>("삭제완료",HttpStatus.OK);
        } catch (DataEntryException e) { // 입력데이터 없음
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (GBookNotFoundException e){ // 글이 존재하지 않음
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (InvalidRequestException e){ // 비밀번호 불일치
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch ( GBookServiceException e){ // 예상치 못한 에러
            log.info(e.getMessage());
            return new ResponseEntity<>("예상치 못한 에러",HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param param
     * @return
     * DataEntryException
     * GBookNotFoundException
     * InvalidRequestException
     */
    @PutMapping("")
    public ResponseEntity<String> updateData(@RequestBody Map<String, Object> param){
        int result = 0;
        if(param.get("gbookNum") == null || param.get("gbookContent") == null){
            return new ResponseEntity<>("데이터가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        log.info("gbookNum: " + param.get("gbookNum"));
        log.info("gbookContent: " + param.get("gbookContent"));
        try{
            result = GBookService.updateOneGBook(
                    Integer.parseInt(param.get("gbookNum").toString()),
                    param.get("gbookContent").toString()
            );
            if(result == 0) return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);
        }catch (DataEntryException e){  // 입력된 데이터가 없습니다.
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (GBookNotFoundException e){  // 변경하려는 글이 존재하지 않습니다.
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (InvalidRequestException e){ // 작성자와 현재 IP가 다릅니다.
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (GBookServiceException e){
            return new ResponseEntity<>("예상치 못한 에러",HttpStatus.NOT_FOUND);
        }
    }
}
