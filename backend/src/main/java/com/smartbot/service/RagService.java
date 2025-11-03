package com.smartbot.service;

/**
 * RAG服务接口（检索增强生成）
 *
 * @author SmartBot Team
 */
public interface RagService {

    /**
     * 生成答案（基于知识库检索）
     *
     * @param sessionId 会话ID
     * @param question 用户问题
     * @return AI生成的答案
     */
    String generateAnswer(String sessionId, String question);

    /**
     * 知识库检索
     *
     * @param query 查询文本
     * @param topK 返回Top-K结果
     * @return 检索结果列表
     */
    // List<KnowledgeSearchResult> search(String query, int topK);
}
