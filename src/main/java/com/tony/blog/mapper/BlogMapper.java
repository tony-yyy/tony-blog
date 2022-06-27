package com.tony.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tony.blog.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    @Update("UPDATE blog SET blog.`comment_count` = (SELECT COUNT(1) FROM COMMENT WHERE comment.`blog_id` = #{id})  WHERE blog.id = #{id}")
    void updateCommentCount(@Param("id") Integer id);
    @Update("UPDATE blog SET blog.`views` = blog.`views` + 1 WHERE blog.id = #{id}")
    void updateViews(@Param("id") Integer id);
}
