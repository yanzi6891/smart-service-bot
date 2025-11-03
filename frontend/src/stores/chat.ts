/**
 * 对话状态管理
 * @author SmartBot Team
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { ConversationVO, MessageVO } from '@/types'

export const useChatStore = defineStore('chat', () => {
  // 当前会话
  const currentConversation = ref<ConversationVO | null>(null)

  // 消息列表
  const messages = ref<MessageVO[]>([])

  // 是否正在发送消息
  const isSending = ref<boolean>(false)

  // 设置当前会话
  function setCurrentConversation(conversation: ConversationVO | null) {
    currentConversation.value = conversation
  }

  // 添加消息
  function addMessage(message: MessageVO) {
    messages.value.push(message)
  }

  // 清空消息
  function clearMessages() {
    messages.value = []
  }

  // 设置发送状态
  function setSending(sending: boolean) {
    isSending.value = sending
  }

  // 重置状态
  function reset() {
    currentConversation.value = null
    messages.value = []
    isSending.value = false
  }

  return {
    currentConversation,
    messages,
    isSending,
    setCurrentConversation,
    addMessage,
    clearMessages,
    setSending,
    reset
  }
})
