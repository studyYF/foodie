package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.UsersMapper;
import com.crossyf.entity.Users;
import com.crossyf.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * 用户表 (Users)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}