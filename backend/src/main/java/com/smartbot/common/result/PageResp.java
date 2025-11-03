package com.smartbot.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @author SmartBot Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页响应结果")
public class PageResp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private List<T> records;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private Long total;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private Integer pageNum;

    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数")
    private Integer pageSize;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Integer pages;

    /**
     * 是否有下一页
     */
    @Schema(description = "是否有下一页")
    private Boolean hasNextPage;

    /**
     * 构建分页响应
     */
    public static <T> PageResp<T> build(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setRecords(records);
        pageResp.setTotal(total);
        pageResp.setPageNum(pageNum);
        pageResp.setPageSize(pageSize);

        // 计算总页数
        int pages = (int) ((total + pageSize - 1) / pageSize);
        pageResp.setPages(pages);

        // 是否有下一页
        pageResp.setHasNextPage(pageNum < pages);

        return pageResp;
    }
}
