package com.shanzhu.platform.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_news")
@ApiModel(value="News对象", description="")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "点赞数量")
    private Integer goods;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "0 为未发布 1为发布 2为删除")
    private Integer status;

    @ApiModelProperty(value = "0为 不是 1为是banner")
    private Integer isBanner;


}
