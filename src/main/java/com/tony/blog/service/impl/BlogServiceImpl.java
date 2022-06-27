package com.tony.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.blog.pojo.Blog;
import com.tony.blog.mapper.BlogMapper;
import com.tony.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    BlogMapper blogMapper;

    @Override
    public Page<Blog> getBlogsOnCondition(Integer page, Integer pageSize, String title, Integer type, boolean onlyPublished, boolean onlyRecommend) {
        Page<Blog> bp = new Page<>(page, pageSize);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "description", "first_picture", "commentabled", "create_time", "flag", "recommend", "published",
                "share_statement", "update_time", "title", "type_id", "user_id", "views", "comment_count");
        if (StringUtils.hasLength(title))
            wrapper.like("title", title);
        if (type != null)
            wrapper.eq("type_id", type);
        if (onlyPublished)
            wrapper.eq("published", true);
        if (onlyRecommend)
            wrapper.eq("recommend", true);
        wrapper.orderByDesc("create_time");
        return blogMapper.selectPage(bp, wrapper);
    }

    @Override
    public void updateCommentCountAndViews(Integer id) {
        blogMapper.updateCommentCount(id); // 更新点赞数
        blogMapper.updateViews(id); // 更新点赞数
    }
}
