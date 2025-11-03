package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum UserRole {

    /**
     * 管理员
     */
    ADMIN("管理员"),

    /**
     * 普通用户
     */
    NORMAL("普通用户");

    private final String description;
}
