package com.pollra.http.board.service;

import com.pollra.http.board.domain.BoardVO;
import com.pollra.http.board.exceptions.exception.DataEntryException;
import com.pollra.http.board.exceptions.exception.NotFoundException;
import com.pollra.http.board.exceptions.exception.PermissionException;
import com.pollra.persistence.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class BoardService {
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public int createBoard(Map<String, Object> param, HttpServletRequest request){
        BoardVO boardVO = new BoardVO();
        int result = 0, insertNum = 0;
        boardVO.setCategory(Integer.parseInt(param.get("newBoardCategory").toString()));
        boardVO.setVisible(Integer.parseInt(param.get("visibleSelectBtn").toString()));
        boardVO.setTitle(param.get("newBoardTitle").toString());
        boardVO.setContent(param.get("newBoardContent").toString());
        boardVO.setDate(
                new SimpleDateFormat("yy.MM.dd kk:mm").format(
                new Date(System.currentTimeMillis()))
        );
        if(request.getSession().getAttribute("lu").equals("")){
            log.info("[postCreate] 로그인 정보가 존재하지 않습니다.");
            throw new PermissionException("로그인 정보가 존재하지 않습니다.");
        }
        if(boardVO.getCategory() <= 0){
            throw new DataEntryException("카테고리를 선택하십시오.");
        }
        boardVO.setWriter(request.getSession().getAttribute("lu").toString());
        try {
            result = boardRepository.insertOneBoardToBoardVO(boardVO);
        }catch (Exception e){
            log.info("[postCreate] 글 작성 실패");
        }
        if(result > 0){
            log.info("[postCreate] 글 작성 성공");
            insertNum = boardRepository.selectBoardNumToBoardVO(boardVO);
            return insertNum;
        }
        throw new DataEntryException("글 작성에 실패했습니다.");
    }

    public void updateBoard(String option, Map<String, Object> param){
        if(option.equals("")) throw new DataEntryException("입력된 데이터가 존재하지 않습니다.");
        switch (option){
            case "visible":
                updateVisible(Integer.parseInt(param.get("target").toString()),Integer.parseInt(param.get("visible").toString()));
                break;
            default:
                throw new DataEntryException("입력된 옵션이 존재하지 않습니다.");
        }
    }

    public void deleteBoard(Map<String, Object> param, HttpServletRequest request){

    }

    public BoardVO selectBoard(int postNum){
        BoardVO boardVO = null;
        log.info("[board] selectBoard : start");
        if(postNum <= 0){
            throw new DataEntryException("입력된 데이터가 잘못되었습니다.");
        }
        if (boardRepository.selectCountToNum(postNum) <= 0) {
            throw new NotFoundException("포스트가 존재하지 않습니다.");
        }
        if(boardRepository.selectVisibleToNum(postNum) <= 0){
            throw new PermissionException("해당 게시물은 비공개 상태입니다.");
        }
        log.info("[board] selectBoard : complete");
        boardVO = boardRepository.selectOneBoardToNum(postNum);
//        log.info("[board]" + boardVO.toString());
        return boardVO;
    }

    private void updateVisible(int num, int visible){
        if(num <= 0) throw new DataEntryException("글 번호가 잘못되었습니다.");
        log.info("[updateVisible] 공개설정 값: "+visible);
        if(visible < 0 || visible > 1) throw new DataEntryException("공개설정의 값이 잘못되었습니다.");
        int result = boardRepository.updateVisibleToNum(num, visible);
        if(result <= 0 ) throw new NotFoundException("업데이트에 실패했습니다.");
        log.info("[updateVisible] 업데이트 성공");
    }
}