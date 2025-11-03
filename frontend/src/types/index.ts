/**
 * 通用类型定义
 * @author SmartBot Team
 */

/** 统一响应结果 */
export interface Result<T = any> {
  code: number
  message: string
  data: T
  timestamp: number
}

/** 分页响应 */
export interface PageResp<T = any> {
  records: T[]
  total: number
  pageNum: number
  pageSize: number
  pages: number
  hasNextPage: boolean
}

/** 分页查询参数 */
export interface PageQuery {
  pageNum: number
  pageSize: number
  orderBy?: string
  sortOrder?: 'ASC' | 'DESC'
}

/** 用户信息 */
export interface UserInfo {
  userId: number
  username: string
  nickname: string
  avatar?: string
  role: 'ADMIN' | 'NORMAL'
}

/** 登录请求 */
export interface LoginReq {
  username: string
  password: string
  captchaCode?: string
}

/** 登录响应 */
export interface LoginResp {
  accessToken: string
  refreshToken: string
  expiresIn: number
  tokenType: string
  userInfo: UserInfo
}

/** 会话状态 */
export type ConversationStatus = 'ACTIVE' | 'CLOSED'

/** 渠道类型 */
export type ChannelType = 'WEB' | 'WECHAT' | 'API' | 'WEBHOOK'

/** 消息角色 */
export type MessageRole = 'USER' | 'ASSISTANT' | 'SYSTEM'

/** 意图类型 */
export type IntentType =
  | 'PRODUCT_INQUIRY'
  | 'ORDER_INQUIRY'
  | 'REFUND_INQUIRY'
  | 'COMPLAINT'
  | 'GREETING'
  | 'OTHER'

/** 会话VO */
export interface ConversationVO {
  sessionId: string
  userId: number
  channel: ChannelType
  status: ConversationStatus
  messageCount: number
  lastMessage?: string
  lastMessageTime?: string
  createTime: string
  updateTime: string
}

/** 消息VO */
export interface MessageVO {
  messageId: string
  sessionId: string
  role: MessageRole
  content: string
  messageType: string
  intent?: IntentType
  isFromKnowledgeBase: boolean
  knowledgeSource?: KnowledgeSource
  tokensUsed?: number
  costTime?: number
  createTime: string
}

/** 知识来源 */
export interface KnowledgeSource {
  documentId: number
  documentTitle: string
  similarity: number
}

/** 创建会话请求 */
export interface CreateConversationReq {
  channel: ChannelType
  userId?: number
  metadata?: Record<string, any>
}

/** 发送消息请求 */
export interface SendMessageReq {
  content: string
  messageType?: string
  metadata?: Record<string, any>
}

/** 知识文档VO */
export interface DocumentVO {
  documentId: number
  title: string
  fileName: string
  fileType: string
  fileSize: number
  filePath: string
  category: string
  status: 'PENDING' | 'PROCESSING' | 'COMPLETED' | 'FAILED'
  processProgress: number
  chunkCount: number
  embeddingModel: string
  failureReason?: string
  createTime: string
  updateTime: string
}

/** 统计数据VO */
export interface StatisticsVO {
  overview: {
    totalConversations: number
    totalMessages: number
    avgMessagesPerConversation: number
    avgResponseTime: number
    satisfactionRate: number
  }
  channelDistribution: Record<string, number>
  intentDistribution: Record<string, number>
  dailyTrend: Array<{
    date: string
    conversationCount: number
    messageCount: number
    satisfactionRate: number
  }>
  knowledgeBaseUsage: {
    hitRate: number
    avgSimilarity: number
    topDocuments: Array<{
      documentId: number
      documentTitle: string
      hitCount: number
    }>
  }
}
