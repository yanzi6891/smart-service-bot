package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话状态枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum ConversationStatus {

    /**
     * 进行中
     */
    ACTIVE("进行中"),

    /**
     * 已关闭
     */
    CLOSED("已关闭");

    private final String description;
}
