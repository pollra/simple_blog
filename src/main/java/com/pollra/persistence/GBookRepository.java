package com.pollra.persistence;

import com.pollra.http.gbook.domain.GBookVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GBookRepository {
    /**
     * 데이터 하나를 입력
     * @param
     * @return
     */
    @Insert("INSERT into public.gbook (num, writer, contents, date, time)" +
            " VALUES (DEFAULT, #{writer}, #{contents}, #{date}, #{time})")
    public int insertOneGBookToGBookVO(GBookVO gBookVO);

    /**
     * 데이터가 존재하는지 확인
     * @param num
     * @return
     */
//    @Select("SELECT COUNT(*) FROM public.gbook WHERE num = #{num}")
//    public int selectGBookCheckToNum(@Param("num") int num);

    /**
     * 해당 번호의 데이터를 가져옴
     * @param num
     * @return
     */
    @Select("SELECT * FROM public.gbook WHERE num = #{num}")
    public GBookVO selectOneGBookToNum(@Param("num") int num);

    /**
     * 모든 데이터를 가져옴
     * @return
     */
    @Select("SELECT * FROM public.gbook ORDER BY num DESC")
    public List<GBookVO> selectAllGBook();

    /**
     * 하나의 데이터를 삭제
     * @param num
     * @param writer
     * @return
     */
    @Delete("DELETE FROM public.gbook WHERE num = #{num} AND writer = #{writer}")
    public int deleteOneGBookToNum(@Param("num") int num, @Param("writer") String writer);

    /**
     * 데이터 업데이트
     * @param num
     * @param content
     * @return
     */
    @Update("UPDATE public.gbook SET contents = #{content} WHERE num = #{num}")
    public int updateOneGBookToNumAndContents(@Param("num") int num, @Param("content") String content);
}
