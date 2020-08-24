package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.UserAddress;
import com.crossyf.entity.bo.AddressBO;

/**
 * 用户地址表 (UserAddress)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 新增用户地址
     *
     * @param addressBO 地址bo
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改用户地址信息
     *
     * @param addressBO 地址bo
     */
    void updateUserAddress(AddressBO addressBO);


    /**
     * 删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    void deleteUserAddress(String userId, String addressId);


    /**
     * 设置用户默认地址
     *
     * @param userId    用户地址
     * @param addressId 地址id
     */
    void updateUserAddressToBeDefault(String userId, String addressId);
}