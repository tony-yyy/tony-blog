package com.tony.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tony.blog.pojo.Blog;
import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.service.BlogService;
import com.tony.blog.utils.MarkDownUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = "application/json;charset=utf-8")
public class UserBlogController {
    private static final Logger logger = LoggerFactory.getLogger(UserBlogController.class);
    @Autowired
    BlogService blogService;

    @RequestMapping("/blogs/recommend")
    public List<Blog> getRecommendBlogs(){
        return blogService.getBlogsOnCondition(1, 6,  null, null, true, true).getRecords();
    }

    @RequestMapping("/blog/detail/{blogId}")
    public ResultInfo blogDetail(@PathVariable("blogId") Integer blogId){
        ResultInfo<Blog> resultInfo = new ResultInfo<>();
        Blog blog = blogService.getById(blogId);
        String content = blog.getContent();
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
//        System.out.println(blog.getContent());
        if (blog != null){
            /*更新评论*/
            /*更新访问量*/
            blogService.updateCommentCountAndViews(blogId);
            resultInfo.setData(blog);
            resultInfo.setError(false);
        }else {
            resultInfo.setError(true);
            resultInfo.setErrorInfo("id 不存在！");
        }
        return resultInfo;
    }

    @RequestMapping("/blogs/{page}")
    public Page<Blog> getBlogsOnCondition(
            @PathVariable(value = "page") Integer page
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
            , @RequestParam(value = "title", required = false) String title
            , @RequestParam(value = "typeId", required = false) Integer typeId){
        return blogService.getBlogsOnCondition(page, pageSize,  title, typeId, true, false);
    }
}
