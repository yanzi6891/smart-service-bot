package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道类型枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum ChannelType {

    /**
     * 网页
     */
    WEB("网页"),

    /**
     * 微信
     */
    WECHAT("微信"),

    /**
     * API接入
     */
    API("API接入"),

    /**
     * Webhook
     */
    WEBHOOK("Webhook");

    private final String description;
}
