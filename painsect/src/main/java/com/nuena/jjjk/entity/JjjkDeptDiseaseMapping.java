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
 * 久久健康网-科室疾病对应表
 * </p>
 *
 * @author rgb
 * @since 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_dept_disease_mapping")
public class JjjkDeptDiseaseMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 科室id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 网站的的科室id
     */
    @TableField("dept_wz_id")
    private String deptWzId;

    /**
     * 科室名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 疾病库主表id
     */
    @TableField("dis_lib_id")
    private Long disLibId;

    /**
     * 疾病id
     */
    @TableField("dis_id")
    private String disId;

    /**
     * 疾病名称
     */
    @TableField("dis_name")
    private String disName;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
