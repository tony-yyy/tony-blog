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
@ApiModel(value="TUser对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String avatar;

    private Date createTime;

    private String email;

    private String nickname;

    private String password;

    private Date updateTime;

    private String username;


}
