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
 * @since 2020-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("med_record_analyze")
public class RecordAnalyze implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("mode_id")
    private Long modeId;

    @TableField("rec_title")
    private String recTitle;

    @TableField("map_type")
    private String mapType;

    @TableField("map_key")
    private String mapKey;

    @TableField("map_value")
    private String mapValue;


}
