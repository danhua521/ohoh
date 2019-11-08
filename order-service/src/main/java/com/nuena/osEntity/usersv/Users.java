package com.nuena.osEntity.usersv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author rgb
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USERS_UUID", type = IdType.UUID)
    private String usersUuid;

    @TableField("USERS_ID")
    private Integer usersId;

    @TableField("OPENID")
    private String openid;

    @TableField("WX_NAME")
    private String wxName;

    @TableField("WX_HEAD_URL")
    private String wxHeadUrl;

    @TableField("HEAD_URL")
    private String headUrl;

    @TableField("GENDER")
    private String gender;

    @TableField("UNIONID")
    private String unionid;

    @TableField("NAME")
    private String name;

    @TableField("AGE")
    private Integer age;

    @TableField("MOBILE")
    private String mobile;

    @TableField("MARRY")
    private String marry;

    @TableField("SEX")
    private String sex;

    @TableField("IS_FOLLOW")
    private String isFollow;

    @TableField("IS_FOLLOW_TIME")
    private Date isFollowTime;

    @TableField("NO_FOLLOW_TIME")
    private Date noFollowTime;

    @TableField("SOURCE_UUID")
    private String sourceUuid;

    @TableField("SOURCE_TYPE")
    private String sourceType;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_NAME")
    private String createName;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_NAME")
    private String updateName;

    @TableField("DEL_TIME")
    private Date delTime;

    @TableField("DEL_NAME")
    private String delName;

    @TableField("IS_DEL")
    private String isDel;


}
