package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.UserAddressMapper;
import com.crossyf.entity.UserAddress;
import com.crossyf.service.UserAddressService;
import org.springframework.stereotype.Service;

/**
 * 用户地址表 (UserAddress)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

}