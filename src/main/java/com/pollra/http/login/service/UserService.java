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

    /**
     * 정보 수정
     * 옵션 2가지
     * 비밀번호 변경(option, newPassword)
     * 이름 변경(option, newName)
     *
     * @param param
     * @param request
     */
    public void updateUser(Map<String, Object> param, HttpServletRequest request){
        log.info("[Uu] updateUser start");

        // 수정 데이터 추출
        HttpSession session = request.getSession();

        // 로그인 유무 체크
        if(session.getAttribute("lu").equals("")){
            log.info("[Uu] 로그인값이 존재하지 않습니다.");
            throw new PermissionException("데이터가 올바르지 않습니다.");
        }
        try {
            // 옵션 불러오기
            String option = param.get("option").toString().trim();
            if (option.equals("pw")) {
                String newPassword = param.get("newPassword").toString().trim();
                if(newPassword.equals("")){
                    log.info("[Uu] 입력된 데이터중 필수항목이 누락되었습니다: newPassword");
                    throw new DataEntryException("데이터가 올바르지 않습니다.");
                }
                if(userRepository.updateOneUserToIdAndPassword(session.getAttribute("lu").toString(), newPassword)>0){
                    log.info("[Uu] 데이터가 성공적으로 전송되었습니다.");
                    return;
                }
                log.info("[Uu] 데이터 전송과정 문제발생 id: "+session.getAttribute("lu").toString()+" / password: "+ newPassword);
                throw new UserServiceException("데이터 전송과정에서 문제가 발생했습니다.");
            } else if (option.equals("name")) {
                String newName = param.get("newName").toString().trim();
                if(newName.equals("")){
                    log.info("[Uu] 입력된 데이터중 필수항목이 누락되었습니다: newName");
                    throw new DataEntryException("데이터가 올바르지 않습니다.");
                }
                if(userRepository.updateOneUserToIdAndName(session.getAttribute("lu").toString(), newName)>0){
                    log.info("[Uu] 데이터가 성공적으로 전송되었습니다.");
                    return;
                }
                log.info("[Uu] 데이터 전송과정 문제발생 id: "+session.getAttribute("lu").toString()+" / name: "+ newName);
                throw new UserServiceException("데이터 전송과정에서 문제가 발생했습니다.");
            } else if(option.equals("pwName")){
                String newName = param.get("newName").toString().trim();
                String newPassword = param.get("newPassword").toString().trim();
                if(newName.equals("") && newPassword.equals("")){
                    log.info("[Uu] 입력된 데이터중 필수항목이 누락되었습니다: newName, newPassword");
                    throw new DataEntryException("데이터가 올바르지 않습니다.");
                }
                if(newName.equals("")){
                    log.info("[Uu] 입력된 데이터중 필수항목이 누락되었습니다: newName");
                    throw new DataEntryException("데이터가 올바르지 않습니다.");
                }
                if(newPassword.equals("")){
                    log.info("[Uu] 입력된 데이터중 필수항목이 누락되었습니다: newPassword");
                    throw new DataEntryException("데이터가 올바르지 않습니다.");
                }
                if(userRepository.updateOneUserToIdAndPasswordAndName(session.getAttribute("lu").toString(), newName, newPassword)>0){
                    log.info("[Uu] 데이터가 성공적으로 전송되었습니다.");
                    return;
                }
                log.info("[Uu] 데이터 전송과정 문제발생 id: "+session.getAttribute("lu").toString()+" / name: "+ newName + " / password: " + newPassword);
                throw new UserServiceException("데이터 전송과정에서 문제가 발생했습니다.");
            }else {
                log.info("[Uu] data entry is incorrect. option: "+ option);
                throw new DataEntryException("데이터가 올바르지 않습니다.");
            }
        }catch (NullPointerException e){
            log.info("[Uu] data entry is null.");
            throw new DataEntryException("데이터가 올바르지 않습니다.");
        }
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

    // 로그아웃
    public void logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = "";
        try{
            loginUser = session.getAttribute("lu").toString().trim();
        } catch ( NullPointerException e){
            log.info("[lo] 로그인 되어있지 않습니다.");
            throw new PermissionException("로그인 되어있지 않습니다.");
        }
        if(loginUser.equals("")){
            log.info("[lo] 해당 아이디를 찾을 수 없습니다.");
            throw new UserNotFoundException("해당 아이디를 찾을 수 없습니다.");
        }
        session.setAttribute("lu","");
    }

}