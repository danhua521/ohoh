package com.nuena.lantone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author rgb
 * @since 2020-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_record_analyze")
public class RecordAnalyze implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 病历ID
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 标准模块id
     */
    @TableField("mode_id")
    private Long modeId;

    /**
     * 医院模板名称
     */
    @TableField("rec_title")
    private String recTitle;

    /**
     * 医院模板下属类型
     */
    @TableField("map_type")
    private String mapType;

    /**
     * 模板中的key
     */
    @TableField("map_key")
    private String mapKey;


}
