<template>
  <el-container class="admin-layout">
    <el-aside width="200px">
      <div class="logo">
        <h2>管理后台</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="admin-menu"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/admin/conversations">
          <el-icon><ChatDotRound /></el-icon>
          <span>会话管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/knowledge">
          <el-icon><Document /></el-icon>
          <span>知识库</span>
        </el-menu-item>
        <el-menu-item index="/admin/settings">
          <el-icon><Setting /></el-icon>
          <span>系统设置</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header height="60px">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentPageTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-button link @click="goToChat">
            <el-icon><Back /></el-icon>
            返回对话
          </el-button>
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="32">
                {{ userStore.userInfo?.nickname?.charAt(0) }}
              </el-avatar>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
/**
 * 管理后台布局
 * @author SmartBot Team
 */
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { logout } from '@/api/auth'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 当前激活菜单
const activeMenu = computed(() => route.path)

// 当前页面标题
const currentPageTitle = computed(() => {
  const title = route.meta.title as string
  return title || '管理后台'
})

// 返回对话
const goToChat = () => {
  router.push('/chat')
}

// 处理下拉菜单命令
const handleCommand = async (command: string) => {
  if (command === 'logout') {
    try {
      await logout()
      userStore.logout()
      router.push('/login')
      ElMessage.success('退出成功')
    } catch (error) {
      console.error('退出失败:', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.admin-layout {
  width: 100%;
  height: 100%;

  .el-aside {
    background: #001529;

    .logo {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 60px;
      color: white;

      h2 {
        margin: 0;
        font-size: 18px;
      }
    }

    .admin-menu {
      border-right: none;
      background: #001529;

      :deep(.el-menu-item) {
        color: rgba(255, 255, 255, 0.65);

        &:hover,
        &.is-active {
          color: white;
          background: #1890ff;
        }
      }
    }
  }

  .el-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: white;
    border-bottom: 1px solid #e4e7ed;
    padding: 0 24px;

    .header-right {
      display: flex;
      align-items: center;
      gap: 16px;
    }

    .user-info {
      cursor: pointer;
    }
  }

  .el-main {
    background: #f5f7fa;
    padding: 24px;
  }
}
</style>
