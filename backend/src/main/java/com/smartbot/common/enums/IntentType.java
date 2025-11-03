package com.smartbot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 意图类型枚举
 *
 * @author SmartBot Team
 */
@Getter
@AllArgsConstructor
public enum IntentType {

    /**
     * 产品咨询
     */
    PRODUCT_INQUIRY("产品咨询"),

    /**
     * 订单查询
     */
    ORDER_INQUIRY("订单查询"),

    /**
     * 退款咨询
     */
    REFUND_INQUIRY("退款咨询"),

    /**
     * 投诉建议
     */
    COMPLAINT("投诉建议"),

    /**
     * 打招呼
     */
    GREETING("打招呼"),

    /**
     * 其他
     */
    OTHER("其他");

    private final String description;
}
