package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息角色枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum MessageRole {

    /**
     * 用户
     */
    USER("用户"),

    /**
     * 助手
     */
    ASSISTANT("助手"),

    /**
     * 系统
     */
    SYSTEM("系统");

    private final String description;
}
