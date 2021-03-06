package com.pollra.http.comment.service;

import com.pollra.http.comment.domain.CommentVO;
import com.pollra.http.comment.domain.CommentViewDTO;
import com.pollra.http.comment.exceptions.exception.CommentServiceException;
import com.pollra.http.comment.exceptions.exception.DataEntryException;
import com.pollra.http.comment.exceptions.exception.NotFoundException;
import com.pollra.persistence.CommentRepository;
import com.pollra.tool.data.InspectionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private CommentRepository commentRepository;
    private InspectionTool inspectionTool;
    private HttpServletRequest request;

    public CommentService(CommentRepository commentRepository, HttpServletRequest request) {
        this.commentRepository = commentRepository;
        this.inspectionTool = new InspectionTool();
        this.request = request;
    }

    public int insertOneComment(Map<String, Object> param) throws CommentServiceException {
        int result = 0;
        CommentVO commentVO = new CommentVO();
        HttpSession session = request.getSession();
        String errStack = "";
        String loginUser = "";
        try {
            errStack+="b";
            commentVO.setBoard(Integer.parseInt(param.get("board").toString().split(";")[0]));
            errStack+="w";
            if(session.getAttribute("lu").toString().equals("")){
                loginUser = inspectionTool.whatYourName(request);
                log.info("현재 로그인되어있지 않습니다. ip로 비교합니다: " + loginUser);
            }else{
                loginUser = session.getAttribute("lu").toString();
                log.info("현재 로그인되어있습니다. 아이디로 비교합니다: "+ loginUser);
            }
            errStack+="l";
            errStack+=loginUser;
            commentVO.setWriter(loginUser);
            errStack+="c";
            commentVO.setContent(inspectionTool.stringDataTagCheck(param.get("content").toString()));
            errStack+="p";
            commentVO.setPassword(param.get("password").toString());
            errStack+="d";
            commentVO.setDate(new SimpleDateFormat("yy.MM.dd akk:mm.sss").format(new Date(System.currentTimeMillis())));
            errStack+="a";
        }catch (Exception e){
            log.info("데이터 입력이 올바르지 않습니다: "+errStack);
            throw new DataEntryException("데이터 입력이 올바르지 않습니다.");
        }

        if(!(commentVO.board_content_password_check()=="")){
            log.info("빈 값이 있습니다:"+commentVO.board_content_password_check());
            throw new DataEntryException("빈 값이 존재합니다.");
        }
        result = commentRepository.insertOneCommentToCommentVO(commentVO);
        if(result <= 0){
            throw new DataEntryException("데이터 입력에 실패했습니다.");
        }
        return result;
    }

    public List<CommentViewDTO> getCommentList(int board){
        if(board <= 0) throw new DataEntryException("데이터는 0이 들어올 수 없습니다.");
        List<CommentViewDTO> commentList = commentRepository.selectListCommentVOToBoard(board);
        if(commentList.size() <= 0 || commentList.get(0) == null) throw new NotFoundException("댓글 리스트가 존재하지 않습니다.");
        return commentList;
    }

    public CommentVO getComment(int num){
        if(num <= 0) throw new DataEntryException("데이터는 0이 들어올 수 없습니다.");
        CommentVO comment = commentRepository.selectOneCommentVOToNum(num);
        if(comment == null) throw new NotFoundException("댓글의 번호가 잘못되었습니다.");
        return comment;
    }


    public int updateOneComment(Map<String, Object> param) {
        // password와 num, 변경하고자하는 content를 받음.
        CommentVO commentVO = new CommentVO();
        HttpSession session = request.getSession();
        String loginUser = "";
        try{
            log.info("1");
            commentVO.setPassword(param.get("password").toString());
            log.info("2");
            commentVO.setNum(Integer.parseInt(param.get("num").toString()));
            log.info("3");
            commentVO.setContent(inspectionTool.stringDataTagCheck(param.get("content").toString()));
            log.info("4");
        }catch (Exception e){
            log.info("넘어온 데이터가 정상적이지 않습니다.");
            throw new DataEntryException("데이터가 정상적이지 않습니다.");
        }
        log.info(commentVO.getContent());
        if(commentVO.getNum() <= 0) throw new DataEntryException("해당 댓글을 찾을 수 없습니다.");
        if(commentVO.getPassword().equals("")) throw new DataEntryException("패스워드를 확인할 수 없습니다.");
        if(commentVO.getContent().equals("")) throw new DataEntryException("입력된 데이터가 존재하지 않습니다.");

        loginUser = inspectionTool.whatYourName(request);
        if(commentRepository.selectOneCommentCountToNumAndWriter(commentVO.getNum(), loginUser)<=0)
            throw new DataEntryException("권한이 없습니다.");
        if(commentRepository.selectOneCommentCountToNumAndPassword(commentVO.getNum(), commentVO.getPassword())<=0)
            throw new DataEntryException("비밀번호가 다릅니다.");
        int result = commentRepository.updateOneCommentToNumAndContent(commentVO.getNum(), commentVO.getContent());
        if(result <= 0)throw new DataEntryException("데이터 수정에 실패했습니다.");
        return result;
    }

    public int deleteOneComment(Map<String, Object> param){
        CommentVO commentVO = new CommentVO();
        HttpSession session = request.getSession();
        String loginUser = "";
        try{
            commentVO.setNum(Integer.parseInt(param.get("num").toString()));
            commentVO.setPassword(param.get("password").toString());
            commentVO.setWriter(inspectionTool.whatYourName(request));
        }catch (Exception e){
            log.info("넘어온 데이터가 정상적이지 않습니다.");
            throw new DataEntryException("데이터가 정상적이지 않습니다.");
        }
        if(commentVO.getNum() <= 0) throw new DataEntryException("해당 댓글을 찾을 수 없습니다.");
        if(commentVO.getPassword().equals("")) throw new DataEntryException("패스워드를 확인할 수 없습니다.");

        if(session.getAttribute("lu").toString().equals("")){
            loginUser = inspectionTool.whatYourName(request);
            log.info("현재 로그인되어있지 않습니다. ip로 비교합니다: " + loginUser);
        }else{
            loginUser = session.getAttribute("lu").toString();
            log.info("현재 로그인되어있습니다. 아이디로 비교합니다: "+ loginUser);
        }
        if(!(loginUser.equals("pollra")))
            if(commentRepository.selectOneCommentCountToNumAndWriter(commentVO.getNum(), loginUser) <= 0)
                throw new DataEntryException("권한이 없습니다.");
        if(commentRepository.selectOneCommentCountToNumAndPassword(commentVO.getNum(), commentVO.getPassword())<=0)
            throw new DataEntryException("비밀번호가 다릅니다.");
        int result = commentRepository.deleteOneCommentToNum(commentVO.getNum(), commentVO.getPassword());
        if(result <= 0) throw new DataEntryException("데이터 삭제에 실패했습니다.");
        return result;
    }
}