package com.smartbot.domain.vo;

import com.smartbot.common.enums.IntentType;
import com.smartbot.common.enums.MessageRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息视图对象
 *
 * @author SmartBot Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "消息视图对象")
public class MessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private String messageId;

    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private String sessionId;

    /**
     * 角色
     */
    @Schema(description = "角色：USER/ASSISTANT/SYSTEM")
    private MessageRole role;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String content;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型：TEXT/IMAGE/VOICE")
    private String messageType;

    /**
     * 意图识别结果
     */
    @Schema(description = "意图识别结果")
    private IntentType intent;

    /**
     * 是否来自知识库
     */
    @Schema(description = "是否来自知识库")
    private Boolean isFromKnowledgeBase;

    /**
     * 知识来源信息
     */
    @Schema(description = "知识来源信息")
    private KnowledgeSource knowledgeSource;

    /**
     * Token消耗数
     */
    @Schema(description = "Token消耗数")
    private Integer tokensUsed;

    /**
     * 响应耗时（毫秒）
     */
    @Schema(description = "响应耗时（毫秒）")
    private Integer costTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KnowledgeSource implements Serializable {
        @Schema(description = "文档ID")
        private Long documentId;

        @Schema(description = "文档标题")
        private String documentTitle;

        @Schema(description = "相似度得分")
        private Double similarity;
    }
}
