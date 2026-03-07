package com.usts.shortlink.admin.common.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.usts.shortlink.admin.common.biz.user.UserContext;
import com.usts.shortlink.admin.common.biz.user.UserInfoDTO;
import com.usts.shortlink.admin.common.convention.exception.ClientException;
import com.usts.shortlink.admin.dao.entity.UserDO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.usts.shortlink.admin.common.constant.RedisCacheConstant.TOKEN_LOGIN;

/**
 * 登录态拦截器
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public UserLoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = getToken(request);
        if (StrUtil.isBlank(token)) {
            throw new ClientException("用户token不能为空");
        }
        String userJson = stringRedisTemplate.opsForValue().get(TOKEN_LOGIN + token);
        if (StrUtil.isBlank(userJson)) {
            throw new ClientException("用户token不存在或已失效");
        }
        UserDO userDO = JSON.parseObject(userJson, UserDO.class);
        if (userDO == null || userDO.getId() == null) {
            throw new ClientException("用户登录信息不存在");
        }
        UserContext.setUser(UserInfoDTO.builder()
                .userId(String.valueOf(userDO.getId()))
                .username(userDO.getUsername())
                .realName(userDO.getRealName())
                .token(token)
                .build());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.removeUser();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            return token;
        }
        String authorization = request.getHeader("Authorization");
        if (StrUtil.startWithIgnoreCase(authorization, "Bearer ")) {
            return StrUtil.subSuf(authorization, 7);
        }
        return request.getParameter("token");
    }
}
