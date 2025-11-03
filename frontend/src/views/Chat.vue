<template>
  <div class="chat-container">
    <div class="chat-header">
      <div class="header-left">
        <h1>smart-service-bot</h1>
        <span class="subtitle">智能客服助手</span>
      </div>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <div class="user-info">
            <el-avatar :size="32" :src="userStore.userInfo?.avatar">
              {{ userStore.userInfo?.nickname?.charAt(0) }}
            </el-avatar>
            <span class="username">{{ userStore.userInfo?.nickname }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="admin">管理后台</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div class="chat-body" ref="chatBodyRef">
      <!-- 欢迎消息 -->
      <div v-if="messages.length === 0" class="welcome-message">
        <el-icon :size="60" color="#409eff"><ChatDotRound /></el-icon>
        <h2>您好！我是智能客服助手</h2>
        <p>有什么可以帮助您的吗？</p>
      </div>

      <!-- 消息列表 -->
      <div v-else class="message-list">
        <div
          v-for="message in messages"
          :key="message.messageId"
          :class="['message-item', message.role.toLowerCase()]"
        >
          <div class="message-avatar">
            <el-avatar v-if="message.role === 'USER'" :size="36">
              {{ userStore.userInfo?.nickname?.charAt(0) }}
            </el-avatar>
            <el-avatar v-else :size="36" style="background: #409eff">
              <el-icon><Robot /></el-icon>
            </el-avatar>
          </div>
          <div class="message-content">
            <div class="message-header">
              <span class="message-sender">
                {{ message.role === 'USER' ? '我' : 'AI助手' }}
              </span>
              <span class="message-time">{{ formatTime(message.createTime) }}</span>
            </div>
            <div class="message-text">{{ message.content }}</div>
            <!-- 知识来源标签 -->
            <div v-if="message.isFromKnowledgeBase && message.knowledgeSource" class="message-source">
              <el-tag type="success" size="small" effect="plain">
                <el-icon><Document /></el-icon>
                来源: {{ message.knowledgeSource.documentTitle }}
                (相似度: {{ (message.knowledgeSource.similarity * 100).toFixed(1) }}%)
              </el-tag>
            </div>
          </div>
        </div>
      </div>

      <!-- 正在输入提示 -->
      <div v-if="chatStore.isSending" class="typing-indicator">
        <el-avatar :size="36" style="background: #409eff">
          <el-icon><Robot /></el-icon>
        </el-avatar>
        <div class="typing-dots">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
    </div>

    <div class="chat-footer">
      <div class="input-wrapper">
        <el-input
          v-model="inputMessage"
          type="textarea"
          :rows="3"
          placeholder="请输入您的问题..."
          :disabled="chatStore.isSending"
          @keyup.enter.exact="handleSend"
        />
        <el-button
          type="primary"
          :loading="chatStore.isSending"
          :disabled="!inputMessage.trim()"
          @click="handleSend"
        >
          <el-icon v-if="!chatStore.isSending"><Promotion /></el-icon>
          {{ chatStore.isSending ? '发送中...' : '发送' }}
        </el-button>
      </div>
      <div class="input-tip">
        <el-text size="small" type="info">按 Enter 发送，Shift + Enter 换行</el-text>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * 聊天页面
 * @author SmartBot Team
 */
import { ref, nextTick, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createConversation, sendMessage } from '@/api/chat'
import { logout } from '@/api/auth'
import { useUserStore } from '@/stores/user'
import { useChatStore } from '@/stores/chat'
import type { MessageVO } from '@/types'

const router = useRouter()
const userStore = useUserStore()
const chatStore = useChatStore()

// 聊天区域引用
const chatBodyRef = ref<HTMLElement>()

// 输入消息
const inputMessage = ref('')

// 消息列表
const messages = computed(() => chatStore.messages)

// 初始化会话
onMounted(async () => {
  if (!chatStore.currentConversation) {
    try {
      const conversation = await createConversation({
        channel: 'WEB',
        userId: userStore.userInfo?.userId
      })
      chatStore.setCurrentConversation(conversation)
    } catch (error) {
      console.error('创建会话失败:', error)
      ElMessage.error('创建会话失败')
    }
  }
})

// 发送消息
const handleSend = async () => {
  const content = inputMessage.value.trim()
  if (!content) return

  const sessionId = chatStore.currentConversation?.sessionId
  if (!sessionId) {
    ElMessage.error('会话不存在')
    return
  }

  // 添加用户消息
  const userMessage: MessageVO = {
    messageId: `temp_${Date.now()}`,
    sessionId,
    role: 'USER',
    content,
    messageType: 'TEXT',
    isFromKnowledgeBase: false,
    createTime: new Date().toISOString()
  }
  chatStore.addMessage(userMessage)

  // 清空输入框
  inputMessage.value = ''

  // 滚动到底部
  scrollToBottom()

  // 发送消息到后端
  chatStore.setSending(true)
  try {
    const response = await sendMessage(sessionId, { content })
    chatStore.addMessage(response)
    scrollToBottom()
  } catch (error) {
    console.error('发送消息失败:', error)
    ElMessage.error('发送消息失败')
  } finally {
    chatStore.setSending(false)
  }
}

// 滚动到底部
const scrollToBottom = async () => {
  await nextTick()
  if (chatBodyRef.value) {
    chatBodyRef.value.scrollTop = chatBodyRef.value.scrollHeight
  }
}

// 格式化时间
const formatTime = (time: string) => {
  const date = new Date(time)
  const now = new Date()
  const diff = now.getTime() - date.getTime()

  if (diff < 60000) {
    return '刚刚'
  } else if (diff < 3600000) {
    return `${Math.floor(diff / 60000)}分钟前`
  } else if (diff < 86400000) {
    return `${Math.floor(diff / 3600000)}小时前`
  } else {
    return date.toLocaleString('zh-CN', {
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  }
}

// 处理下拉菜单命令
const handleCommand = async (command: string) => {
  if (command === 'admin') {
    router.push('/admin/dashboard')
  } else if (command === 'logout') {
    try {
      await logout()
      userStore.logout()
      chatStore.reset()
      router.push('/login')
      ElMessage.success('退出成功')
    } catch (error) {
      console.error('退出失败:', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 60px;
  background: white;
  border-bottom: 1px solid #e4e7ed;

  .header-left {
    h1 {
      font-size: 20px;
      color: #303133;
      margin: 0;
    }

    .subtitle {
      margin-left: 12px;
      font-size: 14px;
      color: #909399;
    }
  }

  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;

    .username {
      font-size: 14px;
      color: #606266;
    }
  }
}

.chat-body {
  flex: 1;
  overflow-y: auto;
  padding: 24px;

  .welcome-message {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    color: #909399;

    h2 {
      margin: 20px 0 10px;
      font-size: 24px;
      color: #303133;
    }

    p {
      font-size: 16px;
    }
  }

  .message-list {
    max-width: 900px;
    margin: 0 auto;
  }

  .message-item {
    display: flex;
    gap: 12px;
    margin-bottom: 24px;

    &.assistant {
      .message-content {
        background: white;
      }
    }

    &.user {
      flex-direction: row-reverse;

      .message-content {
        background: #409eff;
        color: white;

        .message-sender,
        .message-time {
          color: rgba(255, 255, 255, 0.8);
        }
      }
    }
  }

  .message-content {
    max-width: 70%;
    padding: 12px 16px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }

  .message-header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 8px;

    .message-sender {
      font-size: 13px;
      font-weight: 500;
      color: #606266;
    }

    .message-time {
      font-size: 12px;
      color: #909399;
    }
  }

  .message-text {
    font-size: 14px;
    line-height: 1.6;
    white-space: pre-wrap;
    word-wrap: break-word;
  }

  .message-source {
    margin-top: 8px;
  }

  .typing-indicator {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-top: 20px;

    .typing-dots {
      display: flex;
      gap: 4px;
      padding: 12px 16px;
      background: white;
      border-radius: 8px;

      span {
        width: 8px;
        height: 8px;
        background: #909399;
        border-radius: 50%;
        animation: typing 1.4s infinite;

        &:nth-child(2) {
          animation-delay: 0.2s;
        }

        &:nth-child(3) {
          animation-delay: 0.4s;
        }
      }
    }
  }
}

@keyframes typing {
  0%,
  60%,
  100% {
    transform: translateY(0);
  }
  30% {
    transform: translateY(-10px);
  }
}

.chat-footer {
  padding: 20px 24px;
  background: white;
  border-top: 1px solid #e4e7ed;

  .input-wrapper {
    display: flex;
    gap: 12px;
    max-width: 900px;
    margin: 0 auto;

    :deep(.el-textarea) {
      flex: 1;
    }

    .el-button {
      height: auto;
      min-width: 100px;
    }
  }

  .input-tip {
    max-width: 900px;
    margin: 8px auto 0;
    text-align: right;
  }
}
</style>
