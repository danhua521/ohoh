package com.nuena.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nuena.dto.OrderListPageDTO;
import com.nuena.entity.Order;
import com.nuena.facade.OrderFacade;
import com.nuena.vo.OrderListPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author rgb
 * @since 2019-11-07
 */
@Api
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderFacade orderFacade;

    @ApiOperation("列表")
    @PostMapping("/listPage")
    public IPage<OrderListPageDTO> listPage(@RequestBody OrderListPageVO orderListPageVO) {
        return orderFacade.listPage(orderListPageVO);
    }

    @ApiOperation("添加订单")
    @LcnTransaction
    @Transactional
    @PostMapping("/add")
    public String add(@RequestBody Order order) {
        return orderFacade.add(order);
    }


}
