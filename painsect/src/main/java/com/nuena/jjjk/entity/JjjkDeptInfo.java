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
 * 久久健康网-科室信息表
 * </p>
 *
 * @author rgb
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_dept_info")
public class JjjkDeptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 科室id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 科室名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 父科室id
     */
    @TableField("parent_dept_id")
    private String parentDeptId;

    /**
     * 父科室名称
     */
    @TableField("parent_dept_name")
    private String parentDeptName;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
