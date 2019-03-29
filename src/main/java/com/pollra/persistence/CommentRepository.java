package com.pollra.persistence;

import com.pollra.http.comment.domain.CommentVO;
import com.pollra.http.comment.domain.CommentViewDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {

    @Insert("INSERT INTO public.comment (num, board, writer, date, content, password) VALUES (DEFAULT, #{board}, #{writer}, #{date}, #{content}, #{password})")
    public int insertOneCommentToCommentVO(CommentVO commentVO);

    @Update("UPDATE public.comment SET content=#{content} WHERE num=#{num}")
    public int updateOneCommentToNumAndContent(@Param("num") int num, @Param("content") String content);

    @Delete("DELETE FROM public.comment WHERE num = #{num} AND password=#{password}")
    public int deleteOneCommentToNum(@Param("num") int num, @Param("password") String password);

    @Select("SELECT * FROM public.comment WHERE num = #{num}")
    public CommentVO selectOneCommentVOToNum(@Param("num") int num);

    @Select("SELECT count(*) FROM public.comment WHERE num = #{num} AND password = #{password}")
    public int selectOneCommentCountToNumAndPassword(@Param("num") int num, @Param("password") String password);

    @Select("SELECT comment.num, comment.writer, comment.date, comment.content FROM public.comment WHERE board = #{board} ORDER BY comment.date DESC")
    public List<CommentViewDTO> selectListCommentVOToBoard(@Param("board") int board);

    @Select("SELECT count(*) FROM public.comment WHERE num = #{num} AND writer = #{writer}")
    public int selectOneCommentCountToNumAndWriter(@Param("num")int num, @Param("writer")String writer);
}
