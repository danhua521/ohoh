package com.nuena.service.impl;

import com.nuena.entity.Users;
import com.nuena.mapper.UsersMapper;
import com.nuena.service.UsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
