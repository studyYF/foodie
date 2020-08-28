package com.crossyf.service.center;

import com.crossyf.entity.Users;
import com.crossyf.entity.bo.center.CenterUserBO;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */

public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 结果
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId 用户id
     * @param centerUserBO 用户bo
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId 用户id
     * @param faceUrl 用户头像地址
     * @return 结果
     */
    Users updateUserFace(String userId, String faceUrl);
}
