package com.tony.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.blog.pojo.Comment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
public interface CommentService extends IService<Comment> {

    List<Comment> getComments(Long blogId);

    void sendMail(Comment fromComment, String toEmail);

    int deleteComment(Long commentId);
}
