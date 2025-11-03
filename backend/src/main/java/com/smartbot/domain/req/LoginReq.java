package com.smartbot.domain.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录请求
 *
 * @author SmartBot Team
 */
@Data
@Schema(description = "登录请求")
public class LoginReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码", required = true, example = "admin123")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码", example = "ABCD")
    private String captchaCode;
}
