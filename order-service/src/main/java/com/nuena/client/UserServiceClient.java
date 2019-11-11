package com.nuena.client;

import com.nuena.osEntity.usersv.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/7 16:56
 */
@FeignClient(value = "userService")
public interface UserServiceClient {

    @PostMapping("/userInfo/getUsersListByIds")
    List<Users> getUsersListByIds(@RequestBody List<String> ids);

    @PostMapping("/userInfo/addUser")
    String addUser(@RequestBody Users users);

}
