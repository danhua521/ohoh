package com.nuena.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuena.dto.OrderListPageDTO;
import com.nuena.facade.OrderFacade;
import com.nuena.vo.OrderListPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rgb
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderFacade orderFacade;

    @PostMapping("/listPage")
    public IPage<OrderListPageDTO> listPage(@RequestBody OrderListPageVO orderListPageVO){
        return orderFacade.listPage(orderListPageVO);
    }

}
