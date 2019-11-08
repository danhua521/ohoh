package com.nuena.service.impl;

import com.nuena.entity.Order;
import com.nuena.mapper.OrderMapper;
import com.nuena.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rgb
 * @since 2019-11-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
