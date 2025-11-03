/**
 * 用户状态管理
 * @author SmartBot Team
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserInfo } from '@/types'

export const useUserStore = defineStore(
  'user',
  () => {
    // 状态
    const token = ref<string>('')
    const userInfo = ref<UserInfo | null>(null)

    // 设置Token
    function setToken(newToken: string) {
      token.value = newToken
    }

    // 设置用户信息
    function setUserInfo(info: UserInfo) {
      userInfo.value = info
    }

    // 登出
    function logout() {
      token.value = ''
      userInfo.value = null
    }

    // 是否已登录
    const isLoggedIn = computed(() => !!token.value)

    return {
      token,
      userInfo,
      isLoggedIn,
      setToken,
      setUserInfo,
      logout
    }
  },
  {
    persist: true // 持久化到localStorage
  }
)
