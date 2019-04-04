package com.pollra.http.gbook.service;

import com.pollra.http.gbook.domain.GBookVO;
import com.pollra.http.gbook.exceptions.*;
import com.pollra.persistence.GBookRepository;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GBookService {
    private static final Logger log = LoggerFactory.getLogger(GBookService.class);

    private GBookRepository GBookRepository;

    public GBookService(GBookRepository GBookRepository) {
        this.GBookRepository = GBookRepository;
    }

    /**
     * 입력데이터
     * boardTitle : 보드 이름
     * boardContents : 보드 내용
     *
     * 값들을 받아 게시물 하나를 데이터베이스에 저장한다.
     * @param
     * @return
     * -1 : 데이터가 입력되지 않았습니다.
     */
    public void createGBook(String content, HttpServletRequest request) throws GBookServiceException {
        int dataEntryResult;
        GBookVO insertNewGBook = new GBookVO();
        HttpSession session = request.getSession();
        String writer = request.getRemoteAddr();
        // 데이터를 정리
        try {
             String LuData = session.getAttribute("lu").toString().trim();
             if(!LuData.equals("")){
                 writer = LuData;
             }
        } catch (NullPointerException e) {
            log.info("[GBc] writer is null.");
        }
        insertNewGBook.setWriter(writer.trim());
        insertNewGBook.setContents(content.trim());
        insertNewGBook.setDate(
                new SimpleDateFormat("yy.MM.dd")
                        .format(new Date(System.currentTimeMillis()))
        );
        insertNewGBook.setTime(
                new SimpleDateFormat("kk:mm")
                        .format(new Date(System.currentTimeMillis()))
        );
        // 받아온 데이터를 검사
        if(insertNewGBook.check() < 1) {
            log.info("[c]작성자를 확인할 수 없습니다.");
            throw new UserNotFoundException("작성자를 확인할 수 없습니다.");
        }

        dataEntryResult = GBookRepository.insertOneGBookToGBookVO(insertNewGBook);
        if(dataEntryResult <= 0){
            log.info("[c]데이터 저장에 실패했습니다.");
            throw new DataEntryException("데이터 저장에 실패했습니다.");
        }
        // 데이터 저장에 성공
    }

    /**
     * 값을 받아 데이터를 하나 가져옴
     * @param entryBoardNum
     * @return
     * @throws GBookServiceException
     */
    public GBookVO selectOneGBook(int entryBoardNum) throws GBookServiceException {
        GBookVO resultBoardItem;
        // 글번호를 받는다.
//        int entryBoardNum = Integer.parseInt(request.getParameter("boardNum"));

        // 받은 글번호로 조회
        resultBoardItem = GBookRepository.selectOneGBookToNum(entryBoardNum);
        if(resultBoardItem == null){
            log.info("[s]해당 게시글이 존재하지 않습니다.");
            throw new GBookNotFoundException("[s]해당 게시글이 존재하지 않습니다.");
        }

//        // 해당글이 존재한다면 그 글을 리턴
//        view.addObject("oneBoard", resultBoardItem);
        return resultBoardItem;
    }

    public List<GBookVO> selectAllGBookList() throws GBookServiceException {
        List<GBookVO> GBookVOList;

        GBookVOList = GBookRepository.selectAllGBook();
        if(GBookVOList.isEmpty()){
            log.info("[sA]게시글이 존재하지 않습니다.");
            throw new GBookNotFoundException("[sA]게시글이 존재하지 않습니다.");
        }
        return GBookVOList;
    }


    /**
     * 데이터를 받고 글을 삭제
     * @param
     * @throws GBookServiceException
     * DataEntryException
     * GBookNotFoundException
     * InvalidRequestException
     */
    public void deleteOneGBook(int num, HttpServletRequest request) throws GBookServiceException {
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession();
        String loginUser = "";
        try {
            loginUser = session.getAttribute("lu").toString();
        }catch (NullPointerException e){
            log.info("[d] 로그인 되지 않은 유저입니다.");
        }
        if(num == 0 || ip.equals("")){
            log.info("[d]입력되지 않은 데이터가 존재합니다.");
            throw new DataEntryException("입력되지 않은 데이터가 존재합니다.");
        }
        // 삭제할 글이 존재하는지 확인
        GBookVO targetBoard = GBookRepository.selectOneGBookToNum(num);
        if(targetBoard.check() <= 0){
            log.info("[d]삭제하려는 글이 존재하지 않습니다.");
            throw new GBookNotFoundException("삭제하려는 글을 인식하지 못했습니다.");
        }
        if(ip.equals("0:0:0:0:0:0:0:1")){
            ip = targetBoard.getWriter();
        }
        // 글쓴이의 ip주소가 일치하는지 확인. (비밀번호 대용)
        if(!(ip.equals(targetBoard.getWriter()))){
            log.info("[d]작성자와 현재 IP가 다릅니다.");
            throw new InvalidRequestException("권한이 없습니다.");
        }
        // 삭제
        int result = GBookRepository.deleteOneGBookToNum(num, ip);
        if(result > 0){
            log.info("[d] delete complete.");
        }else if(result <= 0){
            log.info("[d] delete failed.");
        }
    }

    /**
     *
     * @param num
     * @param content
     * @param
     * @return
     * @throws GBookServiceException
     * DataEntryException
     * GBookNotFoundException
     * InvalidRequestException
     */
    public int updateOneGBook(int num, String content, HttpServletRequest request)throws GBookServiceException {
        int result=0;
        String loginUser = "";
        HttpSession session = request.getSession();
        String ip = request.getRemoteAddr();
        try {
            loginUser = session.getAttribute("lu").toString();
        }catch (NullPointerException e){
            log.info("[d] 로그인 되지 않은 유저입니다.");
        }
        // 넘어온 데이터가 null 인지?
        if(num <0 || content.equals("") || ip.equals("")){
            log.info("[u]입력된 데이터가 없습니다.");
            throw new DataEntryException("[u]입력된 데이터가 없습니다.");
        }
        // 해당 데이터가 존재하는지?
        GBookVO targetBoard = GBookRepository.selectOneGBookToNum(num);
        if(targetBoard.check() <= 0){
            log.info("[u]변경하려는 글이 존재하지 않습니다.");
            throw new GBookNotFoundException("[u]변경하려는 글이 존재하지 않습니다.");
        }
        // 관리자 권한
        if(ip.equals("0:0:0:0:0:0:0:1")){
            ip = targetBoard.getWriter();
        }

        // 해당 데이터를 수정할 권한이 있는지?
        if(!(ip.equals(targetBoard.getWriter()))){
            log.info("[u] 접속중인 정보와 글쓴이의 정보가 서로 다릅니다.");
            throw new InvalidRequestException("권한이 없습니다.");
        }
        // 데이터 업데이트 진행
        result = GBookRepository.updateOneGBookToNumAndContents(num, content);
        if(result > 0){
            log.info("[u] update complete.");
        }else if(result <=0 ){
            log.info("[u] update failed.");
        }
        return result;
    }
}
