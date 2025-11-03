/**
 * Axios请求封装
 * @author SmartBot Team
 */
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

/** 创建axios实例 */
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

/** 请求拦截器 */
service.interceptors.request.use(
  (config) => {
    // 添加Token
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

/** 响应拦截器 */
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data

    // 如果返回的不是标准Result格式，直接返回
    if (!res.hasOwnProperty('code')) {
      return res
    }

    // 成功
    if (res.code === 200) {
      return res.data
    }

    // Token过期
    if (res.code === 401 || res.code === 10004 || res.code === 10005) {
      ElMessage.error('登录已过期，请重新登录')
      const userStore = useUserStore()
      userStore.logout()
      window.location.href = '/login'
      return Promise.reject(new Error(res.message || 'Token过期'))
    }

    // 其他业务错误
    ElMessage.error(res.message || '请求失败')
    return Promise.reject(new Error(res.message || '请求失败'))
  },
  (error) => {
    console.error('响应错误:', error)

    if (error.response) {
      const status = error.response.status
      switch (status) {
        case 400:
          ElMessage.error('请求参数错误')
          break
        case 401:
          ElMessage.error('未授权，请登录')
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          break
        case 403:
          ElMessage.error('权限不足')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(`请求失败: ${status}`)
      }
    } else if (error.request) {
      ElMessage.error('网络错误，请检查网络连接')
    } else {
      ElMessage.error('请求配置错误')
    }

    return Promise.reject(error)
  }
)

/** 导出请求方法 */
export default service

/** GET请求 */
export function get<T = any>(url: string, params?: any, config?: AxiosRequestConfig): Promise<T> {
  return service.get(url, { params, ...config })
}

/** POST请求 */
export function post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
  return service.post(url, data, config)
}

/** PUT请求 */
export function put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
  return service.put(url, data, config)
}

/** DELETE请求 */
export function del<T = any>(url: string, params?: any, config?: AxiosRequestConfig): Promise<T> {
  return service.delete(url, { params, ...config })
}
