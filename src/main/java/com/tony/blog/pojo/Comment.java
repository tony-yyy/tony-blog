package com.tony.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TComment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nickname;

    private String email;

    private String content;

    private Long blogId;

    private Long parentCommentId;

    private Boolean isAdminComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    //回复评论
    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();
    @TableField(exist = false)
    private Comment parentComment;
    @TableField(exist = false)
    private String parentNickname;
}
