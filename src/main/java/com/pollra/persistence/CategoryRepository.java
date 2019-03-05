package com.pollra.persistence;

import com.pollra.http.category.domain.CategoryVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository {
    @Insert("INSERT INTO public.category (num, parent, level, name, url, visible) VALUES (DEFAULT, #{parent}, #{level}, #{name}, #{url}, #{visible})")
    public int insertOneCategoryToCategoryVO(CategoryVO categoryVO);

    @Select("SELECT * FROM public.category WHERE visible = 1 ORDER BY num ASC")
    public List<CategoryVO> selectVisibleCategoryList();

    @Select("SELECT * FROM public.category ORDER BY num ASC")
    public List<CategoryVO> selectAllCategoryList();

    @Select("SELECT COUNT(*) FROM public.category" +
            " WHERE num = #{num}")
    public int selectCountCategoryToNum(@Param("num") int num);

    @Delete("DELETE FROM public.category" +
            " WHERE num = #{num}")
    public int deleteOneCategoryToNum(@Param("num") int num);

    @Update("UPDATE public.category SET" +
            " name = #{name}" +
            " WHERE num= #{num}")
    public int updateOneCategoryToCategoryVO(@Param("num") int num, @Param("name") String name);

    @Update("UPDATE public.category SET" +
            " visible = #{visible}" +
            " WHERE num= #{num}")
    public int updateOneCategoryToNumAndVisible(@Param("num") int num, @Param("visible") int visible);
}
