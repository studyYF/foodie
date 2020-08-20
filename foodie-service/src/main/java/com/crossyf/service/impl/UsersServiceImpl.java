package com.crossyf.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.entity.bo.UserBO;
import com.crossyf.entity.enums.Sex;
import com.crossyf.mapper.UsersMapper;
import com.crossyf.entity.Users;
import com.crossyf.service.UsersService;
import com.crossyf.utils.JsonResponse;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户表 (Users)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;


    private static final String USER_FACE = "http://122.152.205" +
            ".72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Override
    public JsonResponse saveUser(UserBO userBO) {
        Users user = new Users();
        user.setId(sid.nextShort());
        user.setUsername(userBO.getUsername());
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        user.setPassword(SecureUtil.md5(userBO.getPassword()));
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.parse("1993-03-08", "yyyy-MM-dd"));
        // 默认性别为 保密
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        boolean success = save(user);
        return success ? JsonResponse.ok(user) : JsonResponse.errorMsg("注册失败");
    }
}