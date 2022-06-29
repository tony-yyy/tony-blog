package com.tony.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Pictures对象", description="")
public class Pictures implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片名")
    @TableField("realFileName")
    private String realfilename;

    @ApiModelProperty(value = "图片在存在服务器硬盘上的名")
    @TableField("saveFileName")
    private String savefilename;

    @ApiModelProperty(value = "扩展名")
    private String ext;

    @ApiModelProperty(value = "存储目录")
    private String dir;

    @ApiModelProperty(value = "大小（字节）")
    private Float size;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "上传时间")
    @TableField("uploadTime")
    private Date uploadtime;

    @ApiModelProperty(value = "更改时间")
    @TableField("changeTime")
    private Date changetime;

    @ApiModelProperty(value = "是否进回收站")
    @TableField("isRecycle")
    private Integer isrecycle;

    @ApiModelProperty(value = "是否分享")
    @TableField("isPublic")
    private Integer ispublic;

    @ApiModelProperty(value = "下载次数")
    @TableField("downLoadTimes")
    private Integer downloadtimes;

    @ApiModelProperty(value = "是否是图片")
    @TableField("isImg")
    private Integer isimg;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "缩略图")
    @TableField("thumbnail")
    private String thumbnail;

}
