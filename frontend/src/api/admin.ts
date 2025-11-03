/**
 * 管理后台相关API
 * @author SmartBot Team
 */
import { get } from '@/utils/request'
import type { StatisticsVO, ConversationVO, PageResp } from '@/types'

/** 获取数据统计 */
export function getStatistics(params: {
  startDate: string
  endDate: string
  channel?: string
}) {
  return get<StatisticsVO>('/v1/admin/statistics', params)
}

/** 获取会话列表 */
export function getConversationList(params: {
  pageNum: number
  pageSize: number
  status?: string
  channel?: string
  userId?: number
  startTime?: string
  endTime?: string
}) {
  return get<PageResp<ConversationVO>>('/v1/admin/conversations', params)
}

/** 获取评价列表 */
export function getEvaluationList(params: {
  pageNum: number
  pageSize: number
  rating?: number
  startTime?: string
  endTime?: string
}) {
  return get('/v1/admin/evaluations', params)
}
