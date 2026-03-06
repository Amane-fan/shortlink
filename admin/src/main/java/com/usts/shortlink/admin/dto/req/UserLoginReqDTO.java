package com.usts.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户登录请求信息
 */
@Data
public class UserLoginReqDTO {

    private String username;

    private String password;

}
