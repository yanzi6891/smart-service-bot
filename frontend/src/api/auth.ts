/**
 * 认证相关API
 * @author SmartBot Team
 */
import { post } from '@/utils/request'
import type { LoginReq, LoginResp } from '@/types'

/** 用户登录 */
export function login(data: LoginReq) {
  return post<LoginResp>('/v1/auth/login', data)
}

/** 用户登出 */
export function logout() {
  return post('/v1/auth/logout')
}

/** 刷新Token */
export function refreshToken(refreshToken: string) {
  return post<string>('/v1/auth/refresh', { refreshToken })
}
