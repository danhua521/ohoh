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
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ORDER_UUID", type = IdType.UUID)
    private String orderUuid;

    @TableField("ORDER_ID")
    private Integer orderId;

    @TableField("ORDER_NUM")
    private String orderNum;

    @TableField("USERS_UUID")
    private String usersUuid;

    @TableField("HOTEL_UUID")
    private String hotelUuid;

    @TableField("SOURCE_TYPE")
    private String sourceType;

    @TableField("DC_TYPE")
    private String dcType;

    @TableField("NAME")
    private String name;

    @TableField("MOBILE")
    private String mobile;

    @TableField("ADDRESS")
    private String address;

    @TableField("MSG")
    private String msg;

    @TableField("TOTAL_MONEY")
    private Double totalMoney;

    @TableField("DC_MONEY")
    private Double dcMoney;

    @TableField("YH_MONEY")
    private Double yhMoney;

    @TableField("YH_TYPE")
    private String yhType;

    @TableField("SPSY_MONEY")
    private Double spsyMoney;

    @TableField("PSSY_MONEY")
    private Double pssyMoney;

    @TableField("U_COUPON_UUID")
    private String uCouponUuid;

    @TableField("ORDER_MONEY")
    private Double orderMoney;

    @TableField("REAL_MONEY")
    private Double realMoney;

    @TableField("ORDER_STATUS")
    private String orderStatus;

    @TableField("PAY_TYPE")
    private String payType;

    @TableField("VC_CODE")
    private String vcCode;

    @TableField("COURIER_NUM")
    private String courierNum;

    @TableField("COURIER_COMPANY")
    private String courierCompany;

    @TableField("INSERT_TIME")
    private Date insertTime;

    @TableField("PAY_TIME")
    private Date payTime;

    @TableField("DV_TIME")
    private Date dvTime;

    @TableField("VC_TIME")
    private Date vcTime;

    @TableField("REMARKS")
    private String remarks;

    @TableField("SETTLE_TIME")
    private Date settleTime;

    /**
     * 是否已经分完钱：0-未分钱，1-已分钱
     */
    @TableField("IS_SETTLE")
    private String isSettle;

    @TableField("DEL_TIME")
    private Date delTime;

    @TableField("IS_DEL")
    private String isDel;


}
