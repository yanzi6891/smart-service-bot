package com.smartbot.domain.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录响应
 *
 * @author SmartBot Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录响应")
public class LoginResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @Schema(description = "刷新令牌")
    private String refreshToken;

    /**
     * 过期时间（秒）
     */
    @Schema(description = "过期时间（秒）")
    private Long expiresIn;

    /**
     * Token类型
     */
    @Schema(description = "Token类型")
    private String tokenType;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private UserInfo userInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo implements Serializable {
        private Long userId;
        private String username;
        private String nickname;
        private String avatar;
        private String role;
    }
}
