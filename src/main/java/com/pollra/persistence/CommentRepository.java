package com.pollra.persistence;

import com.pollra.http.comment.domain.CommentVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {

    @Insert("INSERT INTO public.comment (num, board, writer, date, content) VALUES (DEFAULT, #{board}, #{writer}, #{date}, #{content})")
    public int insertOneCommentToCommentVO(CommentVO commentVO);

    @Update("UPDATE public.comment SET content=#{content} WHERE num=#{num}")
    public int updateOneCommentToNumAndContent(@Param("num") int num, @Param("content") String content);

    @Delete("DELETE FROM public.comment WHERE num = #{num}")
    public int deleteOneCommentToNum(@Param("num") int num);

    @Select("SELECT * FROM public.comment WHERE num = #{num}")
    public CommentVO selectOneCommentVOToNum(@Param("num") int num);

    @Select("SELECT * FROM public.comment WHERE board = #{board} ORDER BY comment.date DESC")
    public List<CommentVO> selectListCommentVOToBoard(@Param("board") int board);

}
