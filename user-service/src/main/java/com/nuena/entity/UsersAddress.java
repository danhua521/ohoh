package com.nuena.entity;

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
 * 
 * </p>
 *
 * @author rgb
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_users_address")
public class UsersAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "U_ADDRESS_UUID", type = IdType.UUID)
    private String uAddressUuid;

    @TableField("U_ADDRESS_ID")
    private Integer uAddressId;

    @TableField("USERS_UUID")
    private String usersUuid;

    @TableField("NAME")
    private String name;

    @TableField("MOBILE")
    private String mobile;

    @TableField("COUNTRY")
    private String country;

    @TableField("COUNTRY_CODE")
    private String countryCode;

    @TableField("PROVINCE")
    private String province;

    @TableField("PROVINCE_CODE")
    private String provinceCode;

    @TableField("CITY")
    private String city;

    @TableField("CITY_CODE")
    private String cityCode;

    @TableField("AREA")
    private String area;

    @TableField("AREA_CODE")
    private String areaCode;

    @TableField("TOWN")
    private String town;

    @TableField("TOWN_CODE")
    private String townCode;

    @TableField("ADDRESS")
    private String address;

    @TableField("IS_DEFAULT")
    private String isDefault;

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
