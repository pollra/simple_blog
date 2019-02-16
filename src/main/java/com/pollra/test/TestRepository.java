package com.pollra.test;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository {
    @Select("SELECT test.text FROM public.test WHERE idx = #{idx}")
    public String getText(@Param("idx") int idx);
}
