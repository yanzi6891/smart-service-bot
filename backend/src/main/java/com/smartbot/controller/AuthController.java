package com.smartbot.controller;

import com.smartbot.common.result.Result;
import com.smartbot.domain.req.LoginReq;
import com.smartbot.domain.resp.LoginResp;
import com.smartbot.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author SmartBot Team
 */
@Tag(name = "认证模块", description = "用户登录、登出、Token刷新")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户名密码登录，返回访问令牌")
    @PostMapping("/login")
    public Result<LoginResp> login(@Valid @RequestBody LoginReq loginReq) {
        LoginResp loginResp = authService.login(loginReq);
        return Result.success(loginResp);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "登出当前用户")
    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: 从StpUtil获取当前用户ID
        // Long userId = StpUtil.getLoginIdAsLong();
        // authService.logout(userId);
        return Result.success();
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token", description = "使用刷新令牌获取新的访问令牌")
    @PostMapping("/refresh")
    public Result<String> refreshToken(@RequestParam String refreshToken) {
        String newAccessToken = authService.refreshToken(refreshToken);
        return Result.success(newAccessToken);
    }
}
