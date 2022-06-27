package com.tony.blog.controller;


import com.tony.blog.pojo.Comment;
import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.service.CommentService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@RestController
@RequestMapping(value = "/comment", produces = "application/json;charset=utf-8")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("/{blogId}")
    public List<Comment> getComments(@PathVariable("blogId") Long blogId){
        return commentService.getComments(blogId);
    }


    @PostMapping("/addComment")
    public ResultInfo addComment(@RequestBody Comment comment, HttpSession session){
        if (comment.getParentCommentId() == null) return null;
        String parentEmail = null;
        if (comment.getParentCommentId() != -1){
            // 获取父评论
            Comment parentComment = commentService.getById(comment.getParentCommentId());
            if (parentComment == null){
                comment.setParentCommentId(-1L);
            }else {
                parentEmail = parentComment.getEmail();
//                comment.setParentComment(parentComment);
                comment.setParentNickname(parentComment.getParentNickname());
            }
        }
        // 判断是否是admin
        Long uid = (Long) session.getAttribute("USER_ID");
        comment.setIsAdminComment(uid != null);
        // 如果是回复评论的，发送邮件
        if (comment.getParentCommentId() != -1){
            try {
                commentService.sendMail(comment, parentEmail);
            }catch (Exception exception){
                System.out.println(exception);
            }
        }
        // 保存评论
        boolean save = commentService.save(comment);
        return new ResultInfo("保存成功", !save, !save?"保持失败":"");
    }

    @RequestMapping("/deleteComment/{id}")
    public ResultInfo deleteComment(@PathVariable("id") Long commentId, HttpSession session){
        // 判断是否是admin
        Long uid = (Long) session.getAttribute("USER_ID");
        if (uid == null) return new ResultInfo(true, "抱歉，您无权限操作！");
        int delete = commentService.deleteComment(commentId);
        return new ResultInfo("删除了" + delete + "条评论", false, "");
    }

}

