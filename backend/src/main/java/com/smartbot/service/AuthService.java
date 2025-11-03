package com.smartbot.service;

import com.smartbot.domain.req.LoginReq;
import com.smartbot.domain.resp.LoginResp;

/**
 * 认证服务接口
 *
 * @author SmartBot Team
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginReq 登录请求
     * @return 登录响应（包含Token）
     */
    LoginResp login(LoginReq loginReq);

    /**
     * 用户登出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    String refreshToken(String refreshToken);
}
