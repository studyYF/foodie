package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.Users;
import com.crossyf.entity.bo.UserBO;
import com.crossyf.utils.JsonResponse;

/**
 * 用户表 (Users)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
public interface UsersService extends IService<Users> {

    /**
     * 保存用户信息
     * @param userBO userBo
     * @return 结果
     */
    JsonResponse saveUser(UserBO userBO);
}