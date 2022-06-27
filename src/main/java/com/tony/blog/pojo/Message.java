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
@ApiModel(value="TMessage对象", description="")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nickname;

    private String email;

    private String content;

    private Date createTime;


}
