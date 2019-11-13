package com.nuena.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.nuena.client.UserServiceClient;
import com.nuena.dto.OrderListPageDTO;
import com.nuena.entity.Order;
import com.nuena.osEntity.usersv.Users;
import com.nuena.service.impl.OrderServiceImpl;
import com.nuena.vo.OrderListPageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/7 14:16
 */
@Component
public class OrderFacade extends OrderServiceImpl {

    @Autowired
    private UserServiceClient userServiceClient;

    public IPage<OrderListPageDTO> listPage(OrderListPageVO orderListPageVO) {
        IPage<Order> iPage = new Page<>(orderListPageVO.getCurrent(), orderListPageVO.getSize());
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.orderByDesc("INSERT_TIME");
        iPage = page(iPage, qw);

        List<String> ids =
                iPage.getRecords().stream().map(i -> i.getUsersUuid()).distinct().collect(Collectors.toList());

        List<Users> usersList = userServiceClient.getUsersListByIds(ids);
        Map<String, Users> usersMap = usersList.stream().collect(Collectors.toMap(Users::getUsersUuid, i -> i));

        List<OrderListPageDTO> orderListPageDTOList = Lists.newArrayList();
        iPage.getRecords().forEach(i -> {
            OrderListPageDTO orderListPageDTO = new OrderListPageDTO();
            orderListPageDTO.setOrder(i);
            orderListPageDTO.setUsers(usersMap.get(i.getUsersUuid()));
            orderListPageDTOList.add(orderListPageDTO);
        });


        IPage<OrderListPageDTO> orderListPageDTOIPage = new Page<>();
        BeanUtils.copyProperties(iPage,orderListPageDTOIPage);
        orderListPageDTOIPage.setRecords(orderListPageDTOList);

        return orderListPageDTOIPage;
    }

    @PostMapping("/add")
    public String add(@RequestBody Order order){
        save(order);

        Users users = new Users();
        users.setWxName(order.getAddress());
        String ss = userServiceClient.addUser(users);

        String sss = null;
        if(sss.equals("")){
            System.out.println(11);
        }

        return order.getOrderUuid()+"--"+ss;
    }


}
