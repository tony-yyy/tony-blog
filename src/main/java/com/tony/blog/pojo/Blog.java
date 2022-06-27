package com.tony.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="TBlog对象", description="")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Boolean commentabled = false;// 必须默认为false，否则出现以下情况：不勾选form表单时，不会提交这个input

    private String content;

    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend = false;

    private Boolean shareStatement = false;

    private String title;

    private Date updateTime;

    private Integer views;

    private Long typeId;

    private Long userId;

    private Integer commentCount;


}
