package com.pollra.http.comment.service;

import com.pollra.http.comment.domain.CommentVO;
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

    public void insertOneComment(Map<String, Object> param, HttpServletRequest request) throws CommentServiceException {
        int result = 0;
        CommentVO commentVO = new CommentVO();
        commentVO.setBoard(Integer.parseInt(param.get("").toString()));
        commentVO.setWriter(request.getRemoteAddr());
        commentVO.setContent(param.get("").toString());
        commentVO.setDate(new SimpleDateFormat("yy.MM.dd kk:mm").format(new Date(System.currentTimeMillis())));

        result = commentRepository.insertOneCommentToCommentVO(commentVO);
        if(result <= 0){
            throw new DataEntryException("데이터 입력에 실패했습니다.");
        }

    }

    public List<CommentVO> getCommentList(int board){
        if(board <= 0) throw new DataEntryException("데이터는 0이 들어올 수 없습니다.");
        List<CommentVO> commentList = commentRepository.selectListCommentVOToBoard(board);
        if(commentList.size() <= 0 || commentList.get(0) == null) throw new NotFoundException("댓글 리스트가 존재하지 않습니다.");

        return commentList;
    }

    public CommentVO getComment(int num){
        if(num <= 0) throw new DataEntryException("데이터는 0이 들어올 수 없습니다.");
        CommentVO comment = commentRepository.selectOneCommentVOToNum(num);
        if(comment == null) throw new NotFoundException("댓글의 번호가 잘못되었습니다.");

        return comment;
    }

}