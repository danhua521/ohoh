package com.nuena.dto;

import com.nuena.entity.Order;
import com.nuena.osEntity.usersv.Users;
import lombok.Data;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/7 14:29
 */
@Data
public class OrderListPageDTO {

    private Order order;

    private Users users;


}
