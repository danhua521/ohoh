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
 * 久久健康网-部位症状对应表
 * </p>
 *
 * @author rgb
 * @since 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jjjk_part_symptom_mapping")
public class JjjkPartSymptomMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部位id
     */
    @TableField("part_id")
    private Long partId;

    /**
     * 网站的部位id
     */
    @TableField("part_wz_id")
    private String partWzId;

    /**
     * 部位名称
     */
    @TableField("part_name")
    private String partName;

    /**
     * 症状库主表id
     */
    @TableField("sym_lib_id")
    private Long symLibId;

    /**
     * 症状id
     */
    @TableField("sym_id")
    private String symId;

    /**
     * 症状名称
     */
    @TableField("sym_name")
    private String symName;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
