/**
 * 对话相关API
 * @author SmartBot Team
 */
import { get, post, put } from '@/utils/request'
import type {
  ConversationVO,
  CreateConversationReq,
  MessageVO,
  SendMessageReq,
  PageResp
} from '@/types'

/** 创建会话 */
export function createConversation(data: CreateConversationReq) {
  return post<ConversationVO>('/v1/chat/conversations', data)
}

/** 发送消息 */
export function sendMessage(sessionId: string, data: SendMessageReq) {
  return post<MessageVO>(`/v1/chat/conversations/${sessionId}/messages`, data)
}

/** 获取会话历史 */
export function getConversationHistory(
  sessionId: string,
  pageNum: number = 1,
  pageSize: number = 20
) {
  return get<PageResp<MessageVO>>(`/v1/chat/conversations/${sessionId}/messages`, {
    pageNum,
    pageSize
  })
}

/** 关闭会话 */
export function closeConversation(sessionId: string, closeReason?: string) {
  return put(`/v1/chat/conversations/${sessionId}/close`, null, {
    params: { closeReason }
  })
}
