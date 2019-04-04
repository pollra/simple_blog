package com.pollra.http.category.controller;

import com.pollra.http.category.domain.CategoryVO;
import com.pollra.http.category.exception.CategoryNotFoundException;
import com.pollra.http.category.exception.CategoryServerInternalException;
import com.pollra.http.category.exception.CategoryServiceException;
import com.pollra.http.category.exception.DataEntryException;
import com.pollra.http.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestController {
    private static final Logger log = LoggerFactory.getLogger(CategoryRestController.class);

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategoryList(HttpServletRequest request){
        log.info("[R!get] 카테고리 GET 명령 실행");
        List<CategoryVO> categoryList;
        try {
            categoryList = categoryService.selectVisibleList(request.getRemoteAddr());
            log.info("[R!get] 데이터 전송 size:"+categoryList.size());
            log.info("[R!get] 데이터 전송 value:"+categoryList.get(0).toString());

            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }catch (CategoryNotFoundException e){
            log.info("[R!get] 해당 데이터가 존재하지 않습니다");
            return new ResponseEntity<>("해당 데이터가 존재하지 않습니다", HttpStatus.NOT_FOUND);
        }catch (CategoryServiceException e) {
            log.info("[R!get] 서버에서 오류가 발생했습니다: "+e.getMessage());
            return new ResponseEntity<>("[R!get] 서버에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/category")
    public ResponseEntity<?> setCategory(@RequestBody Map<String, Object> param, HttpServletRequest request){
        log.info("[R!set] 카테고리 SET 명령 실행");
        String loginUser = "";
        try {
            loginUser = request.getSession().getAttribute("lu").toString();
        }catch (Exception e){
            log.info("권한이 없습니다.1");
            return new ResponseEntity<>("권한이 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(!(loginUser.equals("pollra"))){
            log.info("권한이 없습니다.2");
            return new ResponseEntity<>("권한이 없습니다.",HttpStatus.BAD_REQUEST);
        }
        try {
            categoryService.insertOneData(param);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (DataEntryException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CategoryServiceException e) {
            log.info("[R!set] "+e.getMessage());
            return new ResponseEntity<>("서버 내부 오류",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteCategory(@RequestBody Map<String, Object> param, HttpServletRequest request){
        log.info("[R!del] 카테고리 DELETE 명령 실행");
        if(!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            return new ResponseEntity<>("권한이 없습니다.",HttpStatus.BAD_REQUEST);
        }

        try {
            categoryService.deleteOneCategory(param, request);
        }catch (DataEntryException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (CategoryServiceException e) {
            log.info("[R!del] 데이터 삭제 에러: "+ e.getMessage());
            return new ResponseEntity<>("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @PutMapping("/category")
    public ResponseEntity<?> updateCategory(@RequestBody Map<String, Object> param, HttpServletRequest request){
        log.info("[R!upd] 카테고리 UPDATE 명령 실행");
        try {
            categoryService.updateOneCategory(param, request);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataEntryException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CategoryServerInternalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CategoryServiceException e){
            log.info("[R!upd] 업데이트 에러: "+e.getMessage());
            return new ResponseEntity<>("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/category/vi")
    public ResponseEntity<?> updateCategory_vi(@RequestBody Map<String, Object> param, HttpServletRequest request){
        log.info("[R!upd] 카테고리 VISIBLE 명령 실행");
        try {
            categoryService.updateOneCategory_visible(param, request);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataEntryException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CategoryServerInternalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CategoryServiceException e){
            log.info("[R!upv] 업데이트 에러: "+e.getMessage());
            return new ResponseEntity<>("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
