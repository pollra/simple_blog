package com.pollra.http.login.service;

import com.pollra.http.login.domain.UserVO;
import com.pollra.http.login.exceptions.exception.DataEntryException;
import com.pollra.http.login.exceptions.exception.PermissionException;
import com.pollra.http.login.exceptions.exception.UserNotFoundException;
import com.pollra.http.login.exceptions.exception.UserServiceException;
import com.pollra.persistence.UserRepository;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void createUser(Map<String, Object> param){
        log.info("[Uc] createUser start");
        // 가입 데이터 추출
        UserVO userVO = new UserVO(param);

        // 데이터 유효성 검사
        if(userVO == null) {
            // incorrect : 1. 부정확한, 맞지 않는, 사실이 아닌   2. (규범·규칙에) 맞지 않는
            log.info("[Uc] length of the data is incorrect.");
            throw new DataEntryException("length of the data is incorrect.");
        }
        // DB 입력 시작
        int result = userRepository.insertOneUserToUserVO(userVO);
        if(result <= 0){
            log.info("[Uc] Failed to save data.");
            throw new DataEntryException("Failed to save data.");
        }
    }

    // 정보 수정
    public void updateUser(Map<String, Object> param){
        log.info("[Uu] updateUser start");
        // 수정 데이터 추출
        UserVO inputUserVO = new UserVO(param);
        if(inputUserVO.check()){
            // incorrect : 1. 부정확한, 맞지 않는, 사실이 아닌   2. (규범·규칙에) 맞지 않는
            log.info("[Uu] data entry is incorrect.");
            throw new DataEntryException("data entry is incorrect.");
        }
//        UserVO importedUserVO = userRepository.selectOneUserVOToNum(inputUserVO.getNum());
        // 데이터 업데이트 시도
        if(userRepository.updateOneUserToUserVO(inputUserVO)>0){
            log.info("[Uu] data update complete.");
            return;
        }
        log.info("[Uu] data update failed.");
        throw new UserServiceException("data update failed.");
    }

    // 회원 탈퇴
    public void deleteUser(Map<String, Object> param){
        log.info("[Ud] deleteUser start");
        // 삭제하려는 유저의 정보 추출
        UserVO deleteTargetUser = new UserVO(param);
        UserVO importedUserVO = userRepository.selectOneUserVOToNum(deleteTargetUser.getNum());
        // 데이터 확인
        if(!importedUserVO.check()){
            log.info("[Ud] data entry is incorrect.");
            throw new DataEntryException("data entry is incorrect.");
        }
        // 비밀번호 체크
        if(!deleteTargetUser.getPw().equals(importedUserVO.getPw())){
            log.info("[Ud] password incorrect.");
            throw new PermissionException("password incorrect.");
        }
        // 데이터 삭제 진행
        if(userRepository.deleteOneUserToNum(deleteTargetUser.getNum()) > 0){
            log.info("[Ud] delete complete.");
            return;
        }
        log.info("[Ud] delete failed.");
        throw new UserServiceException("delete failed.");
    }


    // 로그인 액션
    public void loginUser(Map<String, Object> param, HttpServletRequest request){
        log.info("[Ula] loginUser start");
        HttpSession session = request.getSession();
        // 로그인 되어있는지 확인
        try {
            String loginUser = session.getAttribute("lu").toString().trim();
            if(!loginUser.equals("")){
                log.info("[Ula] You are already signed in: "+loginUser);
                throw new UserNotFoundException("이미 로그인 되어있습니다.");
            }
        }catch (NullPointerException e){
            session.setAttribute("lu","");
        }
        // 넘어온 데이터 처리
        String inputId = param.get("userId").toString();
        String inputPw = param.get("userPw").toString();

        // 아이디 있는지 확인
        UserVO userVO = userRepository.selectOneUserVOToUserId(inputId);
        if(userVO == null){
            log.info("[Ul] user not found: "+inputId);
            throw new DataEntryException("해당 유저가 존재하지 않습니다.");
        }
        // 비밀번호 맞는지 확인
        if(!userVO.getPw().equals(inputPw)){
            log.info("[Ul] user password incorrect: "+inputPw);
            throw new DataEntryException("비밀번호가 맞지 않습니다.");
        }
        // 세션에 로그인 처리
        session.setAttribute("lu",inputId);
        log.info("[Ul] login complete.");
    }

    // 로그인 확인
    public String loginCheck(HttpServletRequest request){
        log.info("[Ulc] loginCheck start.");
        HttpSession session = request.getSession();
        try {
            String loginUser = session.getAttribute("lu").toString().trim();
            if(loginUser.equals("")){
                throw new UserNotFoundException("로그인되어있지 않습니다.");
            }else{
                log.info("[Ulc] You are already signed in: "+loginUser);
                return loginUser;
            }
        }catch(NullPointerException e){
            log.info("[Ulc] login data is null");
            throw new UserNotFoundException("로그인되어있지 않습니다.");
        }
    }

}