package com.pollra.http.category.service;

import com.pollra.http.category.domain.CategoryVO;
import com.pollra.http.category.exception.CategoryNotFoundException;
import com.pollra.http.category.exception.CategoryServerInternalException;
import com.pollra.http.category.exception.CategoryServiceException;
import com.pollra.http.category.exception.DataEntryException;
import com.pollra.persistence.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 카테고리 입력
     * @param param
     * @throws CategoryServiceException
     */
    public void insertOneData(Map<String, Object> param) throws CategoryServiceException {
        int parentLevel = Integer.parseInt(param.get("parentLevel").toString());
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setParent(Integer.parseInt(param.get("categoryParent").toString()));
        categoryVO.setLevel(0);
        System.out.println("parent: "+(categoryVO.getParent() > 0));
        if(categoryVO.getParent() > 0){
            categoryVO.setLevel(0);
            if(parentLevel == 0){
                categoryVO.setLevel(1);
            }else if(parentLevel > 0){
                categoryVO.setLevel(2);
            }
        }
        categoryVO.setVisible(1);
        categoryVO.setName(param.get("categoryName").toString());
        categoryVO.setUrl("");
        log.info(parentLevel + " : "+categoryVO.toString());
        // 데이터 검사
        if(categoryVO.check() <= 0){
            throw new DataEntryException("[Ci] name 의 데이터가 빈 문자열입니다.");
        }


        int result = categoryRepository.insertOneCategoryToCategoryVO(categoryVO);
        if(result <= 0) {
            log.info("[Ci] 데이터 입력에 실패했습니다.");
            throw new CategoryServerInternalException("[Ci] 데이터 입력에 실패했습니다.");
        }
    }

    /**
     * 카테고리 리스트 가져오기
     * @param ip
     * @return
     * @throws CategoryServiceException
     */
    public List<CategoryVO> selectVisibleList(String ip) throws CategoryServiceException{
        List<CategoryVO> categoryList;
        if(ip.trim().equals("0:0:0:0:0:0:0:1")){
            categoryList = categoryRepository.selectAllCategoryList();
        }else {
            categoryList = categoryRepository.selectVisibleCategoryList();
        }

        if(categoryList.isEmpty()){  // 카테고리 리스트의 내용이 없음
            throw new CategoryNotFoundException("[Cs] 데이터가 존재하지 않습니다.");
        }
        return categoryList;
    }

    /**
     * 카테고리 삭제
     * @param param
     */
    public void deleteOneCategory(Map<String, Object> param, HttpServletRequest request) throws CategoryServiceException{
        int targetNum = Integer.parseInt(param.get("deleteTarget").toString());
        int result=0;
        if(!request.getSession().getAttribute("lu").toString().equals("")){
            if(request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
                result = categoryRepository.deleteOneCategoryToNum(targetNum);
            }else {
                log.info("[Cd] 권한이 없습니다.");
            }
        }else{
            throw new DataEntryException("[Cd] 권한이 없습니다.");
        }
        if(result > 0){
            log.info("[good] 데이터 삭제 성공");
            return;
        }else {
            throw new DataEntryException("[Cd] 데이터 삭제 실패");
        }
    }

    /**
     * 카테고리 업데이트
     *
     * 카테고리 관련 데이터를 모두 받아 처리함
     * @param param
     * @throws CategoryServiceException
     */
    public void updateOneCategory(Map<String, Object> param) throws CategoryServiceException{
        // 넘어오는 데이터 처리
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setNum(Integer.parseInt(param.get("categoryNum").toString()));
        categoryVO.setParent(Integer.parseInt(param.get("categoryParent").toString()));
        categoryVO.setLevel(Integer.parseInt(param.get("categoryLevel").toString()));
        categoryVO.setUrl(param.get("categoryUrl").toString());
        categoryVO.setName(param.get("categoryName").toString());
        categoryVO.setVisible(Integer.parseInt(param.get("categoryVisible").toString()));

        if(categoryVO.check()<=0){
            throw new DataEntryException("[Cu] 입력된 카테고리의 이름이 빈 문자열입니다.");
        }

        // 해당 데이터가 있는지 확인
        if(categoryRepository.selectCountCategoryToNum(categoryVO.getNum()) <= 0){
            throw new CategoryNotFoundException("[Cu] 해당 카테고리가 존재하지 않습니다.");
        }

        int result = categoryRepository.updateOneCategoryToCategoryVO(categoryVO);

        if(result <= 0){
            throw new CategoryServerInternalException("[Cu] 업데이트 실패.");
        }
    }
}
