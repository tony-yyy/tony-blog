package com.tony.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.blog.pojo.Comment;
import com.tony.blog.mapper.CommentMapper;
import com.tony.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Override
    public List<Comment> getComments(Long blogId) {
        // 查询所有评论
        // 指定blogId 及 parentCommentId 为-1
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        wrapper.eq("parent_comment_id", -1);
        List<Comment> comments = commentMapper.selectList(wrapper);
        // 递归获取评论
        for (Comment comment : comments) {
            List<Comment> replyComments = new ArrayList<>();
            getChildComment(comment, replyComments);
            comment.setReplyComments(replyComments);
        }
        return comments;
    }

    @Override
    public void sendMail(Comment fromComment, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("来自【Tony blog】的回复消息");
        message.setText(fromComment.getNickname() + " 发送给你了一条消息：" + fromComment.getContent());
        message.setTo(toEmail);
        message.setFrom("1605337475@qq.com");
        this.mailSender.send(message);
    }

    @Override
    public int deleteComment(Long commentId) {
        // 递归删除
        // 删除当前
        int i = commentMapper.deleteById(commentId);
        // 根据id查询，所有引用
        UpdateWrapper<Comment> wrapper = new UpdateWrapper<>();
        wrapper.eq("parent_comment_id", commentId);
        List<Comment> childComments = commentMapper.selectList(wrapper);
        if (childComments.size() == 0) return i;
        int deleteCount = i;
        for (Comment childComment : childComments) {
            deleteCount += deleteComment(childComment.getId());
        }
        return deleteCount;
    }


    /**
     * 一对多回复
     * @param comment
     * @param replyComments
     */
    public void getChildComment(Comment comment, List<Comment> replyComments){
        // 根据当前的评论id，获取所有父评论指向当前的评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_comment_id", comment.getId());
        List<Comment> comments = commentMapper.selectList(wrapper);
        // 如果子评论为空，返回
        if (comments.size() == 0) return;
        // 否则，继续去找
        for (Comment co : comments) {
            replyComments.add(co);
            getChildComment(co, replyComments);
            co.setParentCommentId(comment.getId());
            co.setParentNickname(comment.getNickname());
        }
    }


    /**
     * 树形结构评论
     */
/*
    public List<Comment> getChildComment(Comment comment){
        // 根据当前的评论id，获取所有父评论指向当前的评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_comment_id", comment.getId());
        List<Comment> comments = commentMapper.selectList(wrapper);
        // 如果子评论为空，返回
        if (comments.size() == 0) return null;
        // 否则，继续去找
        for (Comment co : comments) {
            List<Comment> childComment = getChildComment(co);
            co.setReplyComments(childComment);
            co.setParentCommentId(comment.getId());
            co.setParentNickname(comment.getNickname());
        }
        return comments;
    }*/
}
