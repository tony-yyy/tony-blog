package com.tony.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.blog.pojo.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
public interface BlogService extends IService<Blog> {

    Page<Blog> getBlogsOnCondition(Integer page, Integer pageSize, String title, Integer type, boolean onlyPublished, boolean onlyRecommend);

    void updateCommentCountAndViews(Integer blogId);
}
