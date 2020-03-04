package com.nuena.jjjk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 久久健康网-身体部位信息表
 * </p>
 *
 * @author rgb
 * @since 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_bodypart")
public class JjjkBodypart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部位id
     */
    @TableField("part_id")
    private String partId;

    /**
     * 部位名称
     */
    @TableField("part_name")
    private String partName;

    /**
     * 父部位id
     */
    @TableField("parent_part_id")
    private String parentPartId;

    /**
     * 父部位名称
     */
    @TableField("parent_part_name")
    private String parentPartName;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
