/**
 * Vue Router配置
 * @author SmartBot Team
 */
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'

/** 路由配置 */
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/chat'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: {
      title: '登录',
      requiresAuth: false
    }
  },
  {
    path: '/chat',
    name: 'Chat',
    component: () => import('@/views/Chat.vue'),
    meta: {
      title: '智能对话',
      requiresAuth: true
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/Admin/Layout.vue'),
    meta: {
      title: '管理后台',
      requiresAuth: true
    },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Admin/Dashboard.vue'),
        meta: {
          title: '数据统计'
        }
      },
      {
        path: 'conversations',
        name: 'Conversations',
        component: () => import('@/views/Admin/Conversations.vue'),
        meta: {
          title: '会话管理'
        }
      },
      {
        path: 'knowledge',
        name: 'Knowledge',
        component: () => import('@/views/Admin/Knowledge.vue'),
        meta: {
          title: '知识库'
        }
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('@/views/Admin/Settings.vue'),
        meta: {
          title: '系统设置'
        }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue'),
    meta: {
      title: '404'
    }
  }
]

/** 创建路由实例 */
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

/** 路由守卫 */
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} - smart-service-bot`
  }

  // 检查是否需要登录
  const userStore = useUserStore()
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})

export default router
