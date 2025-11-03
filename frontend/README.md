# smart-service-bot - 前端应用

## 项目简介

smart-service-bot前端应用是基于Vue 3 + TypeScript + Element Plus构建的现代化Web应用，提供智能对话、知识库管理、数据分析等功能。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.3.8 | 前端框架 |
| TypeScript | 5.2.2 | 类型系统 |
| Vite | 5.0 | 构建工具 |
| Element Plus | 2.4.4 | UI组件库 |
| Pinia | 2.1.7 | 状态管理 |
| Vue Router | 4.2.5 | 路由管理 |
| Axios | 1.6.2 | HTTP客户端 |
| ECharts | 5.4.3 | 数据可视化 |

## 项目结构

```
frontend/
├── public/                    # 静态资源
├── src/
│   ├── api/                   # API接口
│   │   ├── auth.ts           # 认证接口
│   │   ├── chat.ts           # 对话接口
│   │   ├── knowledge.ts      # 知识库接口
│   │   └── admin.ts          # 管理后台接口
│   ├── components/            # 公共组件
│   ├── router/                # 路由配置
│   │   └── index.ts
│   ├── stores/                # 状态管理
│   │   ├── user.ts           # 用户状态
│   │   └── chat.ts           # 对话状态
│   ├── styles/                # 样式文件
│   │   └── index.scss
│   ├── types/                 # TypeScript类型
│   │   └── index.ts
│   ├── utils/                 # 工具函数
│   │   └── request.ts        # Axios封装
│   ├── views/                 # 页面组件
│   │   ├── Login.vue         # 登录页
│   │   ├── Chat.vue          # 聊天页
│   │   ├── Admin/            # 管理后台
│   │   │   ├── Layout.vue    # 后台布局
│   │   │   ├── Dashboard.vue # 数据统计
│   │   │   ├── Conversations.vue  # 会话管理
│   │   │   ├── Knowledge.vue # 知识库
│   │   │   └── Settings.vue  # 系统设置
│   │   └── NotFound.vue      # 404页面
│   ├── App.vue               # 根组件
│   └── main.ts               # 入口文件
├── .env                       # 环境变量
├── .env.development           # 开发环境变量
├── .env.production            # 生产环境变量
├── index.html                 # HTML模板
├── package.json               # 项目依赖
├── tsconfig.json              # TypeScript配置
├── vite.config.ts             # Vite配置
└── README.md                  # 本文档
```

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 配置环境变量

编辑 `.env.development`:

```env
# API基础URL（开发环境通过Vite代理到后端）
VITE_API_BASE_URL=/api
```

### 3. 启动开发服务器

```bash
npm run dev
```

访问: http://localhost:5173

### 4. 构建生产版本

```bash
npm run build
```

构建产物在 `dist/` 目录

### 5. 预览生产构建

```bash
npm run preview
```

## 核心功能

### 1. 用户认证

- 登录/登出
- Token管理
- 路由守卫

**文件位置**: `src/views/Login.vue`, `src/stores/user.ts`, `src/router/index.ts`

### 2. 智能对话

- 创建会话
- 实时消息发送
- 消息历史显示
- 知识来源标注
- 打字指示器

**文件位置**: `src/views/Chat.vue`, `src/stores/chat.ts`

### 3. 管理后台

#### 数据统计
- 对话数/消息数统计
- 渠道分布图表
- 意图分布图表
- 趋势分析

**文件位置**: `src/views/Admin/Dashboard.vue`

#### 会话管理
- 会话列表查询
- 状态筛选
- 详情查看

**文件位置**: `src/views/Admin/Conversations.vue`

#### 知识库管理
- 文档上传
- 文档列表
- 分块查看
- 文档删除

**文件位置**: `src/views/Admin/Knowledge.vue`

#### 系统设置
- AI模型配置
- 知识库参数配置

**文件位置**: `src/views/Admin/Settings.vue`

## API集成

### 请求封装

所有API请求通过 `src/utils/request.ts` 统一封装：

```typescript
import { get, post, put, del } from '@/utils/request'

// GET请求
const data = await get('/v1/resource', { id: 1 })

// POST请求
const result = await post('/v1/resource', { name: 'test' })

// PUT请求
await put('/v1/resource/1', { name: 'updated' })

// DELETE请求
await del('/v1/resource/1')
```

### 响应拦截

- 自动提取 `data` 字段
- 统一错误处理
- Token过期自动跳转登录
- 错误消息提示

### API模块

| 模块 | 文件 | 说明 |
|------|------|------|
| 认证 | `src/api/auth.ts` | 登录、登出、刷新Token |
| 对话 | `src/api/chat.ts` | 创建会话、发送消息、历史查询 |
| 知识库 | `src/api/knowledge.ts` | 文档上传、列表查询、删除 |
| 管理后台 | `src/api/admin.ts` | 统计数据、会话列表、评价列表 |

## 状态管理

使用Pinia进行状态管理，支持持久化存储。

### 用户状态 (useUserStore)

```typescript
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

// 设置Token
userStore.setToken('xxx')

// 设置用户信息
userStore.setUserInfo({ userId: 1, username: 'admin' })

// 登出
userStore.logout()

// 是否已登录
const isLoggedIn = userStore.isLoggedIn
```

### 对话状态 (useChatStore)

```typescript
import { useChatStore } from '@/stores/chat'

const chatStore = useChatStore()

// 设置当前会话
chatStore.setCurrentConversation(conversation)

// 添加消息
chatStore.addMessage(message)

// 清空消息
chatStore.clearMessages()

// 重置状态
chatStore.reset()
```

## 路由配置

### 路由列表

| 路径 | 组件 | 说明 | 是否需要登录 |
|------|------|------|-------------|
| / | - | 重定向到/chat | - |
| /login | Login.vue | 登录页 | ❌ |
| /chat | Chat.vue | 对话页 | ✅ |
| /admin | Admin/Layout.vue | 管理后台布局 | ✅ |
| /admin/dashboard | Dashboard.vue | 数据统计 | ✅ |
| /admin/conversations | Conversations.vue | 会话管理 | ✅ |
| /admin/knowledge | Knowledge.vue | 知识库 | ✅ |
| /admin/settings | Settings.vue | 系统设置 | ✅ |

### 路由守卫

自动检查登录状态，未登录用户访问需要认证的页面会跳转到登录页。

```typescript
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({ path: '/login', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})
```

## 样式规范

### SCSS使用

- 使用SCSS编写样式
- 支持嵌套、变量、混入等特性
- Scoped样式避免污染全局

```vue
<style lang="scss" scoped>
.container {
  padding: 20px;

  .title {
    font-size: 24px;
    color: #333;
  }
}
</style>
```

### Element Plus主题

支持自定义主题和暗黑模式：

```typescript
import 'element-plus/theme-chalk/dark/css-vars.css'
```

## TypeScript类型

所有类型定义在 `src/types/index.ts`：

```typescript
// 统一响应结果
interface Result<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

// 分页响应
interface PageResp<T> {
  records: T[]
  total: number
  pageNum: number
  pageSize: number
}

// 用户信息
interface UserInfo {
  userId: number
  username: string
  nickname: string
  avatar?: string
  role: 'ADMIN' | 'NORMAL'
}

// 消息VO
interface MessageVO {
  messageId: string
  sessionId: string
  role: MessageRole
  content: string
  // ...
}
```

## 开发规范

### 组件命名

- 使用PascalCase命名组件文件: `MyComponent.vue`
- 组件名应具有描述性: `UserProfile.vue`，而不是 `Profile.vue`

### 代码风格

- 使用ESLint和Prettier保持代码一致性
- 2空格缩进
- 单引号字符串
- 分号结尾

```bash
# 格式化代码
npm run format

# Lint检查
npm run lint
```

### Commit规范

```
feat: 新功能
fix: Bug修复
docs: 文档更新
style: 代码格式调整
refactor: 重构
perf: 性能优化
test: 测试相关
chore: 构建/工具相关
```

## 性能优化

### 代码分割

自动分割Vue、Element Plus、ECharts等大型依赖：

```typescript
// vite.config.ts
rollupOptions: {
  output: {
    manualChunks: {
      'vue-vendor': ['vue', 'vue-router', 'pinia'],
      'element-plus': ['element-plus', '@element-plus/icons-vue'],
      'echarts': ['echarts']
    }
  }
}
```

### 路由懒加载

所有路由组件采用懒加载：

```typescript
{
  path: '/admin',
  component: () => import('@/views/Admin/Layout.vue')
}
```

### 组件自动导入

使用unplugin-auto-import和unplugin-vue-components自动导入：

- Vue API自动导入
- Element Plus组件按需导入
- 图标自动注册

## 部署

### 生产构建

```bash
npm run build
```

### Nginx配置

```nginx
server {
  listen 80;
  server_name smartservicebot.com;

  root /var/www/smartservicebot/dist;
  index index.html;

  location / {
    try_files $uri $uri/ /index.html;
  }

  location /api {
    proxy_pass http://localhost:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
  }
}
```

### Docker部署

```dockerfile
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 常见问题

### Q1: npm install失败

**A**: 尝试以下方法：
1. 清除缓存: `npm cache clean --force`
2. 删除node_modules和package-lock.json
3. 使用国内镜像: `npm config set registry https://registry.npmmirror.com`

### Q2: Vite代理不工作

**A**: 检查：
1. 后端是否正常启动在8080端口
2. vite.config.ts中的proxy配置是否正确
3. 重启Vite开发服务器

### Q3: Element Plus图标不显示

**A**: 确保已正确导入图标：

```typescript
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
```

### Q4: TypeScript类型错误

**A**: 运行类型检查：

```bash
vue-tsc --noEmit
```

## 浏览器支持

- Chrome >= 87
- Firefox >= 78
- Safari >= 14
- Edge >= 88

## 许可证

本项目采用 MIT License 开源协议。

---

**技术支持**: support@smartservicebot.com

**项目地址**: https://github.com/yourusername/smart-service-bot
