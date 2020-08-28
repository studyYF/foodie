package com.crossyf.service.impl.center;


import com.crossyf.entity.Users;
import com.crossyf.entity.bo.center.CenterUserBO;
import com.crossyf.mapper.UsersMapper;
import com.crossyf.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    public UsersMapper usersMapper;

    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectById(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateById(updateUser);
        return queryUserInfo(userId);
    }

    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateById(updateUser);
        return queryUserInfo(userId);
    }
}
