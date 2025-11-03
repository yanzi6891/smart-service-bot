package com.smartbot.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartbot.common.enums.ChannelType;
import com.smartbot.common.enums.ConversationStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会话实体
 *
 * @author SmartBot Team
 * @tableName conversation
 */
@Data
@TableName("conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话ID（自增主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话唯一标识
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 渠道：WEB-网页, WECHAT-微信, API-API接入, WEBHOOK-Webhook
     */
    private ChannelType channel;

    /**
     * 状态：ACTIVE-进行中, CLOSED-已关闭
     */
    private ConversationStatus status;

    /**
     * 消息数量
     */
    private Integer messageCount;

    /**
     * 最后一条消息内容
     */
    private String lastMessage;

    /**
     * 最后消息时间
     */
    private LocalDateTime lastMessageTime;

    /**
     * 关闭原因
     */
    private String closeReason;

    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 元数据（JSON格式）
     */
    private String metadata;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记
     */
    @TableLogic
    private Integer deleted;
}
