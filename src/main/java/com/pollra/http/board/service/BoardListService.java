package com.pollra.http.board.service;


import com.pollra.http.board.domain.BoardCategoryDAO;
import com.pollra.http.board.exceptions.exception.NotFoundException;
import com.pollra.persistence.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
}
