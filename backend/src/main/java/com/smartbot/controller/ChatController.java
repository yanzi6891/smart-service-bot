package com.smartbot.controller;

import com.smartbot.common.result.PageResp;
import com.smartbot.common.result.Result;
import com.smartbot.domain.req.CreateConversationReq;
import com.smartbot.domain.req.SendMessageReq;
import com.smartbot.domain.vo.ConversationVO;
import com.smartbot.domain.vo.MessageVO;
import com.smartbot.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 对话控制器
 *
 * @author SmartBot Team
 */
@Tag(name = "对话模块", description = "会话管理、消息发送、历史记录")
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 创建会话
     */
    @Operation(summary = "创建会话", description = "创建新的对话会话")
    @PostMapping("/conversations")
    public Result<ConversationVO> createConversation(@Valid @RequestBody CreateConversationReq req) {
        ConversationVO conversation = chatService.createConversation(req);
        return Result.success(conversation);
    }

    /**
     * 发送消息
     */
    @Operation(summary = "发送消息", description = "向指定会话发送消息并获取AI回复")
    @PostMapping("/conversations/{sessionId}/messages")
    public Result<MessageVO> sendMessage(
            @Parameter(description = "会话ID", required = true) @PathVariable String sessionId,
            @Valid @RequestBody SendMessageReq req) {
        MessageVO message = chatService.sendMessage(sessionId, req);
        return Result.success(message);
    }

    /**
     * 获取会话历史
     */
    @Operation(summary = "获取会话历史", description = "分页查询会话的历史消息")
    @GetMapping("/conversations/{sessionId}/messages")
    public Result<PageResp<MessageVO>> getConversationHistory(
            @Parameter(description = "会话ID", required = true) @PathVariable String sessionId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        PageResp<MessageVO> history = chatService.getConversationHistory(sessionId, pageNum, pageSize);
        return Result.success(history);
    }

    /**
     * 关闭会话
     */
    @Operation(summary = "关闭会话", description = "关闭指定的对话会话")
    @PutMapping("/conversations/{sessionId}/close")
    public Result<Void> closeConversation(
            @Parameter(description = "会话ID", required = true) @PathVariable String sessionId,
            @Parameter(description = "关闭原因") @RequestParam(required = false) String closeReason) {
        chatService.closeConversation(sessionId, closeReason);
        return Result.success();
    }
}
