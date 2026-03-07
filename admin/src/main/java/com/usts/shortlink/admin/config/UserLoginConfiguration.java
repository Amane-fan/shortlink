package com.usts.shortlink.admin.config;

import com.usts.shortlink.admin.common.web.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截器配置
 */
@Configuration
public class UserLoginConfiguration implements WebMvcConfigurer {

    private final UserLoginInterceptor userLoginInterceptor;

    public UserLoginConfiguration(UserLoginInterceptor userLoginInterceptor) {
        this.userLoginInterceptor = userLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/api/short-link/v1/**")
                .excludePathPatterns(
                        "/api/short-link/v1/user",
                        "/api/short-link/v1/user/login",
                        "/api/short-link/v1/user/has-username",
                        "/api/short-link/v1/user/login/check-login"
                );
    }
}
