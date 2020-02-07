package com.nuena.sjjk.entity;

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
 * 39健康网—疾病-护理
 * </p>
 *
 * @author rgb
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sjjk_disease_nurse")
public class SjjkDiseaseNurse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 护理--url
     */
    @TableField("nurse_url")
    private String nurseUrl;

    /**
     * 护理--html
     */
    @TableField("nurse_html")
    private String nurseHtml;

    /**
     * 护理--html解析出的内容
     */
    @TableField("nurse_anaytxt")
    private String nurseAnaytxt;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("remark")
    private String remark;


}
