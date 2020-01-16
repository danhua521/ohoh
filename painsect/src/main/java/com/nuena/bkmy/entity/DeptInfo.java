package com.nuena.bkmy.entity;

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
 * 百科名医网-科室信息表
 * </p>
 *
 * @author rgb
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_dept_info")
public class DeptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 百科名医网-科室id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 百科名医网-科室名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 百科名医网-父科室id
     */
    @TableField("parent_dept_id")
    private String parentDeptId;

    /**
     * 百科名医网-父科室名称
     */
    @TableField("parent_dept_name")
    private String parentDeptName;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
