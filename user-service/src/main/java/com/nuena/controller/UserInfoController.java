package com.nuena.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nuena.entity.Users;
import com.nuena.facade.UsersFacade;
import com.nuena.vo.UserInfoListPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rgb
 * @since 2019-11-06
 */
@Api("用户信息")
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UsersFacade usersFacade;

    @ApiOperation("列表")
    @PostMapping("/listPage")
    public IPage<Users> listPage(@RequestBody UserInfoListPageVO userInfoListPageVO){
        return usersFacade.listPage(userInfoListPageVO);
    }

    @PostMapping("/getUsersListByIds")
    public List<Users> getUsersListByIds(@RequestBody List<String> ids){
        return usersFacade.getUsersListByIds(ids);
    }

    @LcnTransaction
    @Transactional
    @PostMapping("/addUser")
    public String addUser(@RequestBody Users users){
        return usersFacade.addUser(users);
    }



}