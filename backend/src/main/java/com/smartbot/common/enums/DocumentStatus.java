package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档状态枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum DocumentStatus {

    /**
     * 待处理
     */
    PENDING("待处理"),

    /**
     * 处理中
     */
    PROCESSING("处理中"),

    /**
     * 已完成
     */
    COMPLETED("已完成"),

    /**
     * 失败
     */
    FAILED("失败");

    private final String description;
}
