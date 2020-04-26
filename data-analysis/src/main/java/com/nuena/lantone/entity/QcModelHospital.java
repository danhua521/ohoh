package com.nuena.lantone.entity;

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
public class QcModelHospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("hospital_id")
    private Integer hospitalId;

    @TableField("hospital_model_name")
    private String hospitalModelName;

    @TableField("stand_model_id")
    private Long standModelId;

    @TableField("stand_model_name")
    private String standModelName;


}
