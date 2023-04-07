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
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_column")
@ApiModel(value="Column对象", description="")
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = " 主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "专栏名称")
    private String columnName;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "专栏介绍")
    private String columnDescribe;

    @ApiModelProperty(value = "0为未启用 1为启用")
    private Integer status;

    @ApiModelProperty(value = "创建用户")
    private String userId;

}
