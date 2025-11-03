package com.smartbot.domain.vo;

import com.smartbot.common.enums.ChannelType;
import com.smartbot.common.enums.ConversationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会话视图对象
 *
 * @author SmartBot Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会话视图对象")
public class ConversationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private String sessionId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 渠道
     */
    @Schema(description = "渠道：WEB/WECHAT/API/WEBHOOK")
    private ChannelType channel;

    /**
     * 状态
     */
    @Schema(description = "状态：ACTIVE/CLOSED")
    private ConversationStatus status;

    /**
     * 消息数量
     */
    @Schema(description = "消息数量")
    private Integer messageCount;

    /**
     * 最后一条消息
     */
    @Schema(description = "最后一条消息")
    private String lastMessage;

    /**
     * 最后消息时间
     */
    @Schema(description = "最后消息时间")
    private LocalDateTime lastMessageTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
