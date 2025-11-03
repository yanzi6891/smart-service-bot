package com.smartbot.service;

import com.smartbot.common.result.PageResp;
import com.smartbot.domain.req.CreateConversationReq;
import com.smartbot.domain.req.SendMessageReq;
import com.smartbot.domain.vo.ConversationVO;
import com.smartbot.domain.vo.MessageVO;

/**
 * 对话服务接口
 *
 * @author SmartBot Team
 */
public interface ChatService {

    /**
     * 创建会话
     *
     * @param req 创建会话请求
     * @return 会话视图对象
     */
    ConversationVO createConversation(CreateConversationReq req);

    /**
     * 发送消息
     *
     * @param sessionId 会话ID
     * @param req 发送消息请求
     * @return 消息视图对象（AI回复）
     */
    MessageVO sendMessage(String sessionId, SendMessageReq req);

    /**
     * 获取会话历史消息
     *
     * @param sessionId 会话ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 消息列表
     */
    PageResp<MessageVO> getConversationHistory(String sessionId, Integer pageNum, Integer pageSize);

    /**
     * 关闭会话
     *
     * @param sessionId 会话ID
     * @param closeReason 关闭原因
     */
    void closeConversation(String sessionId, String closeReason);
}
