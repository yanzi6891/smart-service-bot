package com.smartbot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smartbot.common.enums.UserStatus;
import com.smartbot.common.exception.BusinessException;
import com.smartbot.dao.mapper.UserMapper;
import com.smartbot.domain.entity.User;
import com.smartbot.domain.req.LoginReq;
import com.smartbot.domain.resp.LoginResp;
import com.smartbot.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 认证服务实现
 *
 * @author SmartBot Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    @Override
    public LoginResp login(LoginReq loginReq) {
        // 1. 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginReq.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException(10001, "用户不存在");
        }

        // 2. 校验密码
        if (!BCrypt.checkpw(loginReq.getPassword(), user.getPassword())) {
            throw new BusinessException(10002, "用户名或密码错误");
        }

        // 3. 检查用户状态
        if (user.getStatus() == UserStatus.DISABLED) {
            throw new BusinessException(10003, "用户已被禁用");
        }

        // 4. 生成Token
        StpUtil.login(user.getUserId());
        String accessToken = StpUtil.getTokenValue();

        // 5. 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);

        // 6. 构建响应
        LoginResp.UserInfo userInfo = LoginResp.UserInfo.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .build();

        return LoginResp.builder()
                .accessToken(accessToken)
                .refreshToken(accessToken) // 简化版，实际应该生成独立的refresh token
                .expiresIn(StpUtil.getTokenTimeout())
                .tokenType("Bearer")
                .userInfo(userInfo)
                .build();
    }

    @Override
    public void logout(Long userId) {
        StpUtil.logout(userId);
        log.info("用户登出成功, userId={}", userId);
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 简化实现：直接续期当前Token
        StpUtil.checkLogin();
        StpUtil.renewTimeout(7200);
        return StpUtil.getTokenValue();
    }
}
