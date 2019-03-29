package com.pollra.http.comment.service;

import com.pollra.http.comment.domain.CommentVO;
import com.pollra.http.comment.domain.CommentViewDTO;
import com.pollra.http.comment.exceptions.exception.CommentServiceException;
import com.pollra.http.comment.exceptions.exception.DataEntryException;
import com.pollra.http.comment.exceptions.exception.NotFoundException;
import com.pollra.persistence.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public int insertOneComment(Map<String, Object> param, HttpServletRequest request) throws CommentServiceException {
        int result = 0;
        CommentVO commentVO = new CommentVO();
        try {
            commentVO.setBoard(Integer.parseInt(param.get("board").toString()));
            commentVO.setWriter(request.getRemoteAddr());
            commentVO.setContent(param.get("content").toString());
            commentVO.setPassword(param.get("password").toString());
            commentVO.setDate(new SimpleDateFormat("yy.MM.dd akk:mm").format(new Date(System.currentTimeMillis())));
        }catch (Exception e){
            log.info("데이터 입력이 올바르지 않습니다.");
            throw new DataEntryException("데이터 입력이 올바르지 않습니다.");
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


    public int updateOneComment(Map<String, Object> param, HttpServletRequest request) {
        // password와 num, 변경하고자하는 content를 받음.
        CommentVO commentVO = new CommentVO();
        try{
            commentVO.setPassword(param.get("password").toString());
            commentVO.setNum(Integer.parseInt(param.get("num").toString()));
            commentVO.setContent(param.get("content").toString());
        }catch (Exception e){
            log.info("넘어온 데이터가 정상적이지 않습니다.");
            throw new DataEntryException("데이터가 정상적이지 않습니다.");
        }
        if(commentVO.getNum() <= 0) throw new DataEntryException("해당 댓글을 찾을 수 없습니다.");
        if(commentVO.getPassword().equals("")) throw new DataEntryException("패스워드가 올바르지 않습니다.");
        if(commentVO.getContent().equals("")) throw new DataEntryException("입력된 데이터가 존재하지 않습니다.");
        if(commentRepository.selectOneCommentCountToNumAndWriter(commentVO.getNum(), request.getRemoteAddr()) <= 0)
            throw new DataEntryException("권한이 없습니다.");
        if(commentRepository.selectOneCommentCountToNumAndPassword(commentVO.getNum(), commentVO.getPassword())<=0)
            throw new DataEntryException("비밀번호가 다릅니다.");
        int result = commentRepository.updateOneCommentToNumAndContent(commentVO.getNum(), commentVO.getContent());
        if(result <= 0)throw new DataEntryException("데이터 수정에 실패했습니다.");
        return result;
    }

    public int deleteOneComment(Map<String, Object> param, HttpServletRequest request){
        CommentVO commentVO = new CommentVO();
        try{
            commentVO.setNum(Integer.parseInt(param.get("num").toString()));
            commentVO.setPassword(param.get("password").toString());
            commentVO.setWriter(request.getRemoteAddr());
        }catch (Exception e){
            log.info("넘어온 데이터가 정상적이지 않습니다.");
            throw new DataEntryException("데이터가 정상적이지 않습니다.");
        }
        if(commentVO.getNum() <= 0) throw new DataEntryException("해당 댓글을 찾을 수 없습니다.");
        if(commentVO.getPassword().equals("")) throw new DataEntryException("패스워드가 올바르지 않습니다.");
        if(commentRepository.selectOneCommentCountToNumAndWriter(commentVO.getNum(), request.getRemoteAddr()) <= 0)
            throw new DataEntryException("권한이 없습니다.");
        if(commentRepository.selectOneCommentCountToNumAndPassword(commentVO.getNum(), commentVO.getPassword())<=0)
            throw new DataEntryException("비밀번호가 다릅니다.");
        int result = commentRepository.deleteOneCommentToNum(commentVO.getNum(), commentVO.getPassword());
        if(result <= 0) throw new DataEntryException("데이터 수정에 실패했습니다.");
        return result;
    }
}