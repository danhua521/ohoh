package com.nuena.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.nuena.entity.Users;
import com.nuena.service.impl.UsersServiceImpl;
import com.nuena.vo.UserInfoListPageVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/6 14:46
 */
@Component
public class UsersFacade extends UsersServiceImpl {

    public IPage<Users> listPage(UserInfoListPageVO userInfoListPageVO) {

        IPage<Users> iPage = new Page<>(userInfoListPageVO.getCurrent(), userInfoListPageVO.getSize());
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(userInfoListPageVO.getWxName()), "WX_NAME", userInfoListPageVO.getWxName());
        qw.orderByDesc("CREATE_TIME");

        return page(iPage, qw);
    }

    public List<Users> getUsersListByIds(List<String> ids){
        QueryWrapper<Users> qw = new QueryWrapper<>();
        return Lists.newArrayList(listByIds(ids));
    }

    public String addUser(Users users){
        this.save(users);
        String ss = null;
        if(ss.equals("")){
            System.out.println(11);
        }

        return users.getUsersUuid();
    }


}
