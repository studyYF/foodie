package com.crossyf.entity.bo;

import lombok.Data;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */
@Data
public class UserBO {

    private String username;

    private String password;

    private String confirmPassword;
}
