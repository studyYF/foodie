package com.crossyf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.entity.bo.AddressBO;
import com.crossyf.entity.enums.YesOrNo;
import com.crossyf.mapper.UserAddressMapper;
import com.crossyf.entity.UserAddress;
import com.crossyf.service.UserAddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户地址表 (UserAddress)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;


    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        Integer defaultAddress = 0;
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", addressBO.getUserId());
        List<UserAddress> userAddresses = userAddressMapper.selectList(queryWrapper);
        if (userAddresses.isEmpty()) {
            defaultAddress = 1;
        }
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);
        newAddress.setId(sid.nextShort());
        newAddress.setIsDefault(defaultAddress);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(newAddress);

    }

    @Override
    public void updateUserAddress(AddressBO addressBO) {
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressBO.getAddressId());
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateById(pendingAddress);
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {
        userAddressMapper.deleteById(addressId);
    }

    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查找默认地址，设置为不默认
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_default", YesOrNo.YES.type);
        queryWrapper.eq("user_id", userId);
        List<UserAddress> list = userAddressMapper.selectList(queryWrapper);
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateById(ua);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateById(defaultAddress);
    }
}