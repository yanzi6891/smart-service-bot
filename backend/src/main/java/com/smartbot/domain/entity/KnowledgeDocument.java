package com.smartbot.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartbot.common.enums.DocumentStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识文档实体
 *
 * @author SmartBot Team
 * @tableName knowledge_document
 */
@Data
@TableName("knowledge_document")
public class KnowledgeDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @TableId(type = IdType.AUTO)
    private Long documentId;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型：PDF, WORD, TXT, MD
     */
    private String fileType;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文档分类
     */
    private String category;

    /**
     * 状态：PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, FAILED-失败
     */
    private DocumentStatus status;

    /**
     * 处理进度（0-100）
     */
    private Integer processProgress;

    /**
     * 分块数量
     */
    private Integer chunkCount;

    /**
     * Embedding模型
     */
    private String embeddingModel;

    /**
     * 失败原因
     */
    private String failureReason;

    /**
     * 上传者ID
     */
    private Long uploaderId;

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
