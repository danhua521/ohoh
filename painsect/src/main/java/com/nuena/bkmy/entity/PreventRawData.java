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
 * 百科名医网-预防html表
 * </p>
 *
 * @author rgb
 * @since 2020-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mybk_prevent_raw_data")
public class PreventRawData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 百科名医网-预防id
     */
    @TableField("pvt_id")
    private String pvtId;

    /**
     * 百科名医网-预防名称
     */
    @TableField("pvt_name")
    private String pvtName;

    /**
     * 百科名医网-预防url
     */
    @TableField("pvt_url")
    private String pvtUrl;

    /**
     * 百科名医网-预防html
     */
    @TableField("pvt_html")
    private String pvtHtml;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;


}
