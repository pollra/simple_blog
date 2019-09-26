package com.pollra.persistence;

import com.pollra.http.login.domain.UserVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    // 회원가입
    @Insert("INSERT INTO public.user (num, id, pw, authority, name)" +
            " VALUES (DEFAULT , #{id}, #{pw}, #{authority}, #{name})")
    int insertOneUserToUserVO(UserVO userVO);

    // 아이디로 회원 찾기
    @Select("SELECT * FROM public.user WHERE id = #{id}")
    UserVO selectOneUserVOToUserId(@Param("id") String id);

    // 회원번호로 회원 찾기
    @Select("SELECT * FROM public.user WHERE num = #{num}")
    UserVO selectOneUserVOToNum(@Param("num") int num);

    // 회원번호로 회원 탈퇴
    @Delete("DELETE FROM public.user WHERE num = #{num}")
    int deleteOneUserToNum(@Param("num") int num);

    // 이름 수정
    @Update("UPDATE public.user SET name = #{name} WHERE id = #{id}")
    int updateOneUserToIdAndName(@Param("id") String id, @Param("name") String name);

    // 비밀번호 수정
    @Update("UPDATE public.user SET pw = #{pw} WHERE id = #{id}")
    int updateOneUserToIdAndPassword(@Param("id") String id, @Param("pw") String newPassword);

    // 이름, 비밀번호 동시수정
    @Update("UPDATE public.user SET pw = #{pw}, name = #{name} WHERE id = #{id}")
    int updateOneUserToIdAndPasswordAndName(@Param("id") String id, @Param("pw") String newPassword, @Param("name") String name);
}
