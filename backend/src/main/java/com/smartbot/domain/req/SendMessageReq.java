package com.smartbot.domain.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送消息请求
 *
 * @author SmartBot Team
 */
@Data
@Schema(description = "发送消息请求")
public class SendMessageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容", required = true, example = "如何办理退款？")
    @NotBlank(message = "消息内容不能为空")
    private String content;

    /**
     * 消息类型：TEXT-文本, IMAGE-图片, VOICE-语音
     */
    @Schema(description = "消息类型", example = "TEXT")
    private String messageType = "TEXT";

    /**
     * 元数据
     */
    @Schema(description = "元数据")
    private Map<String, Object> metadata;
}
