package com.smartbot.domain.req;

import com.smartbot.common.enums.ChannelType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 创建会话请求
 *
 * @author SmartBot Team
 */
@Data
@Schema(description = "创建会话请求")
public class CreateConversationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道类型
     */
    @Schema(description = "渠道类型：WEB/WECHAT/API/WEBHOOK", required = true, example = "WEB")
    @NotNull(message = "渠道类型不能为空")
    private ChannelType channel;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "123")
    private Long userId;

    /**
     * 元数据
     */
    @Schema(description = "元数据")
    private Map<String, Object> metadata;
}
