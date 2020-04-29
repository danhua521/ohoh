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
 * 医院病历文书类型细分类key明细表
 * </p>
 *
 * @author rgb
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_record_analyze_detail")
public class RecordAnalyzeDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主表ID
     */
    @TableField("record_analyze_id")
    private Long recordAnalyzeId;

    /**
     * 医院数据结构化Key
     */
    @TableField("map_key")
    private String mapKey;


}
