package com.pollra.persistence;

import com.pollra.http.board.domain.BoardCategoryDAO;
import com.pollra.http.board.domain.BoardVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {
    @Insert("INSERT INTO public.board (num, writer, title, content, date, visible, category) VALUES (DEFAULT, #{writer}, #{title}, #{content}, #{date}, #{visible}, #{category})")
    public int insertOneBoardToBoardVO(BoardVO boardVO);

    @Select("SELECT * FROM public.board WHERE num = #{num}")
    public BoardVO selectOneBoardToNum(@Param("num") int num);

//    @Select("SELECT * FROM public.board WHERE writer = #{writer}")
//    public BoardVO selectOneBoardToWriter(@Param("writer") String writer);

    @Select("SELECT COUNT(*) FROM public.board WHERE num = #{num}")
    public int selectCountToNum(@Param("num") int num);

    @Select("SELECT board.visible FROM public.board WHERE num = #{num}")
    public int selectVisibleToNum(@Param("num") int num);

    @Select("SELECT board.num FROM public.board WHERE writer = #{writer} and title = #{title} and content = #{content} and date = #{date}")
    public int selectBoardNumToBoardVO(BoardVO boardVO);

//    @Select("SELECT COUNT(*) FROM public.board WHERE writer = #{writer}")
//    public int selectCountToWriter(@Param("writer") String writer);
//
//    @Update("UPDATE public.board SET title = #{title}, content = #{content} WHERE num = #{num}")
//    public int updateOneBoardToTitleAndContent(@Param("num") int num, @Param("title") String title, @Param("content") String content);
//
//    @Delete("DELETE FROM public.board WHRER num = #{num}")
//    public int deleteOneBoardToNum(@Param("num") int num);

    @Select("SELECT board.num, category.name, board.title, board.date, board.visible FROM category INNER JOIN board ON category.num = board.category ORDER BY board.num DESC")
    public List<BoardCategoryDAO> selectAllUsersBoardCategoryDAO();

    @Select("SELECT board.num, category.name, board.title, board.date, board.visible FROM category INNER JOIN board ON category.num = board.category ORDER BY board.num DESC")
    public List<BoardCategoryDAO> selectBoardListToCategory();

    @Update("UPDATE public.board SET visible=#{visible} WHERE num = #{num}")
    public int updateVisibleToNum(@Param("num")int num, @Param("visible") int visible);

    @Update("UPDATE public.board SET category=#{category}, title=#{title}, content=#{content}, visible=#{visible} WHERE num=#{num}")
    public int updateOneBoardToBoardVO(BoardVO boardVO);

//    @Select("SELECT board.num FROM category INNER JOIN board ON category.num = board.category WHERE category.name = #{name} ORDER BY board.num DESC LIMIT 1;")
//    public int selectLastBoardNumToCategoryName(@Param("name") String categoryName);

    @Select("SELECT board.num FROM category INNER JOIN board ON category.num = board.category WHERE category.num = #{num} AND board.visible = 1 ORDER BY board.num DESC LIMIT 1")
    public int selectLastBoardNumToCategoryNum(@Param("num") int categoryName);

    @Select("SELECT * FROM public.board WHERE category=#{category} ORDER BY board.date DESC")
    public List<BoardVO> selectBoardVOToCategory(@Param("category") int category);

    @Select("SELECT board.num FROM public.board ORDER BY board.category DESC LIMIT 1")
    public int selectLastBoardNum();

    @Select("SELECT board.category FROM public.board WHERE num = #{postNum}")
    public int selectCategoryNumToBoardNum(@Param("postNum") int postNum);
}
