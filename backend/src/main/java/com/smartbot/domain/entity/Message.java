package com.smartbot.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartbot.common.enums.IntentType;
import com.smartbot.common.enums.MessageRole;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息实体
 *
 * @author SmartBot Team
 * @tableName message
 */
@Data
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID（自增主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息唯一标识
     */
    private String messageId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 角色：USER-用户, ASSISTANT-助手, SYSTEM-系统
     */
    private MessageRole role;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型：TEXT-文本, IMAGE-图片, VOICE-语音
     */
    private String messageType;

    /**
     * 意图识别结果
     */
    private IntentType intent;

    /**
     * 是否来自知识库
     */
    private Boolean isFromKnowledgeBase;

    /**
     * 知识来源文档ID
     */
    private Long sourceDocumentId;

    /**
     * 知识来源文档标题
     */
    private String sourceDocumentTitle;

    /**
     * 相似度得分
     */
    private Double similarityScore;

    /**
     * Token消耗数
     */
    private Integer tokensUsed;

    /**
     * 响应耗时（毫秒）
     */
    private Integer costTime;

    /**
     * 错误信息（如果失败）
     */
    private String errorMessage;

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
