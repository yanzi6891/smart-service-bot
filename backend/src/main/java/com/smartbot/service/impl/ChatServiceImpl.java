package com.smartbot.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartbot.common.enums.ConversationStatus;
import com.smartbot.common.enums.MessageRole;
import com.smartbot.common.exception.BusinessException;
import com.smartbot.common.result.PageResp;
import com.smartbot.dao.mapper.ConversationMapper;
import com.smartbot.dao.mapper.MessageMapper;
import com.smartbot.domain.entity.Conversation;
import com.smartbot.domain.entity.Message;
import com.smartbot.domain.req.CreateConversationReq;
import com.smartbot.domain.req.SendMessageReq;
import com.smartbot.domain.vo.ConversationVO;
import com.smartbot.domain.vo.MessageVO;
import com.smartbot.service.ChatService;
import com.smartbot.service.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对话服务实现
 *
 * @author SmartBot Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final RagService ragService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConversationVO createConversation(CreateConversationReq req) {
        // 1. 生成会话ID
        String sessionId = "sess_" + IdUtil.getSnowflakeNextIdStr();

        // 2. 创建会话实体
        Conversation conversation = new Conversation();
        conversation.setSessionId(sessionId);
        conversation.setUserId(req.getUserId());
        conversation.setChannel(req.getChannel());
        conversation.setStatus(ConversationStatus.ACTIVE);
        conversation.setMessageCount(0);
        conversation.setMetadata(req.getMetadata() != null ? JSONUtil.toJsonStr(req.getMetadata()) : null);

        // 3. 保存到数据库
        conversationMapper.insert(conversation);

        log.info("创建会话成功, sessionId={}, userId={}, channel={}",
                sessionId, req.getUserId(), req.getChannel());

        // 4. 构建响应
        return ConversationVO.builder()
                .sessionId(sessionId)
                .userId(conversation.getUserId())
                .channel(conversation.getChannel())
                .status(conversation.getStatus())
                .messageCount(0)
                .createTime(conversation.getCreateTime())
                .updateTime(conversation.getUpdateTime())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendMessage(String sessionId, SendMessageReq req) {
        long startTime = System.currentTimeMillis();

        // 1. 检查会话是否存在
        LambdaQueryWrapper<Conversation> conversationWrapper = new LambdaQueryWrapper<>();
        conversationWrapper.eq(Conversation::getSessionId, sessionId);
        Conversation conversation = conversationMapper.selectOne(conversationWrapper);

        if (conversation == null) {
            throw new BusinessException(20001, "会话不存在");
        }

        if (conversation.getStatus() == ConversationStatus.CLOSED) {
            throw new BusinessException(20002, "会话已关闭");
        }

        // 2. 保存用户消息
        Message userMessage = new Message();
        userMessage.setMessageId("msg_" + IdUtil.getSnowflakeNextIdStr());
        userMessage.setSessionId(sessionId);
        userMessage.setRole(MessageRole.USER);
        userMessage.setContent(req.getContent());
        userMessage.setMessageType(req.getMessageType());
        userMessage.setMetadata(req.getMetadata() != null ? JSONUtil.toJsonStr(req.getMetadata()) : null);
        messageMapper.insert(userMessage);

        // 3. 调用RAG服务生成回复
        String aiResponse;
        Long sourceDocumentId = null;
        String sourceDocumentTitle = null;
        Double similarityScore = null;

        try {
            // 调用RAG服务（实际应该返回包含来源信息的结果对象）
            aiResponse = ragService.generateAnswer(sessionId, req.getContent());

            // TODO: 从RAG服务获取知识来源信息
            // RagResult ragResult = ragService.generateAnswerWithSource(sessionId, req.getContent());
            // aiResponse = ragResult.getAnswer();
            // sourceDocumentId = ragResult.getSourceDocumentId();
            // sourceDocumentTitle = ragResult.getSourceDocumentTitle();
            // similarityScore = ragResult.getSimilarity();

        } catch (Exception e) {
            log.error("AI生成回复失败, sessionId={}, error={}", sessionId, e.getMessage(), e);
            aiResponse = "抱歉，当前服务繁忙，请稍后再试。";
        }

        long costTime = (int) (System.currentTimeMillis() - startTime);

        // 4. 保存AI回复消息
        Message assistantMessage = new Message();
        assistantMessage.setMessageId("msg_" + IdUtil.getSnowflakeNextIdStr());
        assistantMessage.setSessionId(sessionId);
        assistantMessage.setRole(MessageRole.ASSISTANT);
        assistantMessage.setContent(aiResponse);
        assistantMessage.setMessageType("TEXT");
        assistantMessage.setIsFromKnowledgeBase(sourceDocumentId != null);
        assistantMessage.setSourceDocumentId(sourceDocumentId);
        assistantMessage.setSourceDocumentTitle(sourceDocumentTitle);
        assistantMessage.setSimilarityScore(similarityScore);
        assistantMessage.setCostTime((int) costTime);
        // TODO: 设置tokensUsed和intent
        messageMapper.insert(assistantMessage);

        // 5. 更新会话信息
        conversation.setMessageCount(conversation.getMessageCount() + 2); // 用户消息 + AI回复
        conversation.setLastMessage(req.getContent());
        conversation.setLastMessageTime(LocalDateTime.now());
        conversationMapper.updateById(conversation);

        log.info("发送消息成功, sessionId={}, messageId={}, costTime={}ms",
                sessionId, assistantMessage.getMessageId(), costTime);

        // 6. 构建响应
        MessageVO.KnowledgeSource knowledgeSource = null;
        if (sourceDocumentId != null) {
            knowledgeSource = MessageVO.KnowledgeSource.builder()
                    .documentId(sourceDocumentId)
                    .documentTitle(sourceDocumentTitle)
                    .similarity(similarityScore)
                    .build();
        }

        return MessageVO.builder()
                .messageId(assistantMessage.getMessageId())
                .sessionId(sessionId)
                .role(MessageRole.ASSISTANT)
                .content(aiResponse)
                .messageType("TEXT")
                .isFromKnowledgeBase(sourceDocumentId != null)
                .knowledgeSource(knowledgeSource)
                .costTime((int) costTime)
                .createTime(assistantMessage.getCreateTime())
                .build();
    }

    @Override
    public PageResp<MessageVO> getConversationHistory(String sessionId, Integer pageNum, Integer pageSize) {
        // 1. 分页查询消息
        Page<Message> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getSessionId, sessionId)
                .orderByAsc(Message::getCreateTime);

        Page<Message> messagePage = messageMapper.selectPage(page, wrapper);

        // 2. 转换为VO
        List<MessageVO> messageVOList = messagePage.getRecords().stream()
                .map(this::convertToMessageVO)
                .collect(Collectors.toList());

        // 3. 构建分页响应
        return PageResp.build(messageVOList, messagePage.getTotal(), pageNum, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeConversation(String sessionId, String closeReason) {
        // 1. 查询会话
        LambdaQueryWrapper<Conversation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conversation::getSessionId, sessionId);
        Conversation conversation = conversationMapper.selectOne(wrapper);

        if (conversation == null) {
            throw new BusinessException(20001, "会话不存在");
        }

        // 2. 更新会话状态
        conversation.setStatus(ConversationStatus.CLOSED);
        conversation.setCloseReason(closeReason);
        conversation.setCloseTime(LocalDateTime.now());
        conversationMapper.updateById(conversation);

        log.info("关闭会话成功, sessionId={}, closeReason={}", sessionId, closeReason);
    }

    /**
     * 转换Message实体为MessageVO
     */
    private MessageVO convertToMessageVO(Message message) {
        MessageVO.KnowledgeSource knowledgeSource = null;
        if (message.getSourceDocumentId() != null) {
            knowledgeSource = MessageVO.KnowledgeSource.builder()
                    .documentId(message.getSourceDocumentId())
                    .documentTitle(message.getSourceDocumentTitle())
                    .similarity(message.getSimilarityScore())
                    .build();
        }

        return MessageVO.builder()
                .messageId(message.getMessageId())
                .sessionId(message.getSessionId())
                .role(message.getRole())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .intent(message.getIntent())
                .isFromKnowledgeBase(message.getIsFromKnowledgeBase())
                .knowledgeSource(knowledgeSource)
                .tokensUsed(message.getTokensUsed())
                .costTime(message.getCostTime())
                .createTime(message.getCreateTime())
                .build();
    }
}
