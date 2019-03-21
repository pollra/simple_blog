package com.pollra.http.board.service;


import com.pollra.http.board.domain.BoardCategoryDAO;
import com.pollra.http.board.domain.BoardVO;
import com.pollra.http.comment.exceptions.exception.NotFoundException;
import com.pollra.persistence.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardListService {
    private static final Logger log = LoggerFactory.getLogger(BoardListService.class);

    private BoardRepository boardRepository;

    public BoardListService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardCategoryDAO> getBoardCategoryList(){
        List<BoardCategoryDAO> boardCategoryDAOS = boardRepository.selectAllUsersBoardCategoryDAO();
        if(boardCategoryDAOS.size() <= 0){
            throw new NotFoundException("작성된 게시물이 존재하지 않습니다.");
        }
        return boardCategoryDAOS;
    }

    /**
     * 포스트의 넘버가 넘어오고
     *
     * @param postNum
     * @return
     */
    public List<BoardVO> getBoardListToCategory(int postNum){
        int category = boardRepository.selectCategoryNumToBoardNum(postNum);
        if(category < 0){
            throw new NotFoundException("존재하지 않는 포스트 번호입니다.");
        }
        if(category <= 0){
            throw new NotFoundException("존재하지 않는 카테고리 입니다.");
        }
        List<BoardVO> list = boardRepository.selectBoardVOToCategory(category);
        if(list.size() <= 0){
            throw new NotFoundException("선택된 카테고리의 글을 찾을 수 없습니다.");
        }
        return list;
    }
}