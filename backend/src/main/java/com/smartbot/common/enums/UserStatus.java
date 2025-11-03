package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    /**
     * 启用
     */
    ENABLED("启用"),

    /**
     * 禁用
     */
    DISABLED("禁用");

    private final String description;
}
