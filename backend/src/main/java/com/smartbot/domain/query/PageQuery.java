package com.smartbot.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * @author SmartBot Team
 */
@Data
@Schema(description = "分页查询基类")
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码（从1开始）
     */
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段", example = "create_time")
    private String orderBy;

    /**
     * 排序方向：ASC-升序, DESC-降序
     */
    @Schema(description = "排序方向", example = "DESC")
    private String sortOrder = "DESC";

    /**
     * 计算偏移量
     */
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
