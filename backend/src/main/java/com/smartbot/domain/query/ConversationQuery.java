package com.smartbot.domain.query;

import com.smartbot.common.enums.ChannelType;
import com.smartbot.common.enums.ConversationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 会话查询对象
 *
 * @author SmartBot Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "会话查询对象")
public class ConversationQuery extends PageQuery {

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
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
}
