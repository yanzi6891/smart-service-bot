# smart-service-bot - 智能AI客服系统

<div align="center">

![smart-service-bot](https://img.shields.io/badge/smart-service-bot-v1.0.0-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![Vue](https://img.shields.io/badge/Vue-3.3-brightgreen)
![License](https://img.shields.io/badge/License-MIT-yellow)

**企业级智能AI客服系统 | 基于大语言模型 + RAG + 多渠道接入**

[在线演示](https://demo.smartservicebot.com) | [快速开始](#快速开始) | [API文档](https://docs.smartservicebot.com) | [贡献指南](#贡献指南)

</div>

---

## 📖 项目简介

smart-service-bot 是一款企业级智能AI客服系统，基于大语言模型（LLM）和检索增强生成（RAG）技术，为企业提供7×24小时智能客户服务。系统支持多轮对话、知识库管理、意图识别、多渠道接入（Web/微信/API），具备完善的管理后台和数据分析能力。

### ✨ 核心特性

#### 🤖 智能对话引擎
- **多轮对话**: 上下文理解，支持连续10轮对话记忆
- **意图识别**: 自动识别用户意图（产品咨询、订单查询、退款、投诉等）
- **智能回复**: 基于GPT-4/通义千问/文心一言的智能生成
- **流式响应**: SSE/WebSocket实时流式输出，提升用户体验

#### 📚 知识库系统 (RAG)
- **文档管理**: 支持PDF/Word/TXT/Markdown等多种格式
- **智能解析**: 自动分块、向量化，最优chunk大小500字
- **向量检索**: Milvus向量数据库，支持语义相似度搜索
- **问答库**: 手动维护高频问答对，优先级匹配
- **来源追溯**: 自动标注答案来源，提高可信度

#### 🎯 管理后台
- **对话监控**: 实时查看所有对话，支持人工接入
- **数据分析**: 对话量、满意度、意图分布、渠道分析
- **知识管理**: 文档上传、编辑、删除，问答对维护
- **用户管理**: 用户权限、角色管理
- **系统配置**: AI模型配置、API密钥管理

#### 🔌 多渠道接入
- **Web SDK**: 一行代码集成网页聊天窗口
- **微信公众号**: 支持微信消息自动回复
- **RESTful API**: 标准API接口，快速集成第三方系统
- **Webhook**: 事件推送，实现业务闭环

#### 🔒 企业级特性
- **高性能**: 支持10,000+并发对话，响应时间<3秒
- **高可用**: 多级缓存（Caffeine + Redis）、异步处理、降级策略
- **安全性**: JWT认证、API签名、数据加密、敏感信息脱敏
- **可扩展**: 微服务架构设计，支持水平扩展

---

## 🏗️ 技术架构

### 技术栈

#### 后端技术栈
| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Spring Boot | 3.2 | 应用框架 |
| Spring AI | 1.0 | AI集成框架 |
| MyBatis Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0 | 关系型数据库 |
| Redis | 7.0 | 缓存/会话存储 |
| Milvus | 2.3+ | 向量数据库 |
| Sa-Token | 1.37 | 权限认证 |
| Knife4j | 4.4 | API文档 |
| Lombok | 1.18 | 简化开发 |
| Hutool | 5.8 | 工具类库 |

#### 前端技术栈
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.3.8 | 前端框架 |
| TypeScript | 5.2.2 | 类型系统 |
| Vite | 5.0 | 构建工具 |
| Element Plus | 2.4.4 | UI组件库 |
| Pinia | 2.1.7 | 状态管理 |
| Axios | 1.6 | HTTP客户端 |
| ECharts | 5.4 | 数据可视化 |
| TailwindCSS | 3.3 | CSS框架 |

#### AI & 向量数据库
| 技术 | 说明 |
|------|------|
| OpenAI GPT-4 | 主力大语言模型 |
| 通义千问 | 国产大模型 |
| 文心一言 | 百度大模型 |
| text-embedding-ada-002 | OpenAI向量化模型 |
| Milvus | 开源向量数据库 |

### 系统架构图

```
┌─────────────────────────────────────────────────────────────────┐
│                          客户端层                                 │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│  │  Web端   │  │  微信端   │  │  API接入 │  │  移动端   │       │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘       │
└─────────────────────────────────────────────────────────────────┘
                              ↓ HTTPS
┌─────────────────────────────────────────────────────────────────┐
│                         接入层 (Nginx)                            │
│              负载均衡 | SSL终止 | 静态资源 | 反向代理              │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                         应用层 (Spring Boot)                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ 对话管理服务  │  │ 知识库服务    │  │ 管理后台服务  │          │
│  │ ChatService  │  │ KnowledgeService│ AdminService  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ 渠道接入服务  │  │ 用户认证服务  │  │ 配置管理服务  │          │
│  │ ChannelService│ │  AuthService   │ │ ConfigService │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                        AI引擎层 (Spring AI)                       │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  LLM服务     │  │  RAG服务     │  │  向量化服务   │          │
│  │ AiModelService│ │  RagService   │ │EmbeddingService│         │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│         ↓                  ↓                   ↓                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  OpenAI API  │  │ 向量检索引擎  │  │  Prompt管理   │          │
│  │  通义千问     │  │ VectorSearch │  │PromptTemplate │          │
│  │  文心一言     │  │              │  │              │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                          数据层                                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │    MySQL     │  │    Redis     │  │   Milvus     │          │
│  │  业务数据     │  │  缓存/会话    │  │  向量数据库   │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

### 核心流程图

#### RAG对话流程

```
用户提问
   ↓
[1] 意图识别
   ├─ 规则匹配（快速）
   └─ LLM识别（准确）
   ↓
[2] 上下文加载
   ├─ Redis缓存读取
   └─ 滑动窗口（最近10轮）
   ↓
[3] 知识检索
   ├─ 问题向量化 (Embedding)
   ├─ 向量相似度搜索 (Milvus)
   ├─ 阈值过滤 (similarity > 0.7)
   └─ Top-K选择 (K=5)
   ↓
[4] Prompt构建
   ├─ 系统提示词
   ├─ 知识库上下文
   ├─ 历史对话
   └─ 用户问题
   ↓
[5] LLM生成
   ├─ 调用GPT-4/通义千问
   ├─ 流式输出 (SSE)
   └─ Token计数
   ↓
[6] 答案后处理
   ├─ 来源标注
   ├─ 敏感词过滤
   └─ 格式化输出
   ↓
返回给用户
```

---

## 🚀 快速开始

### 环境要求

- **Java**: 17+
- **Node.js**: 18+
- **MySQL**: 8.0+
- **Redis**: 7.0+
- **Milvus**: 2.3+
- **Maven**: 3.8+

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/smart-service-bot.git
cd smart-service-bot
```

### 2. 数据库初始化

#### MySQL数据库

```bash
# 1. 创建数据库
mysql -u root -p

CREATE DATABASE smart_service_bot DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. 导入初始化脚本
mysql -u root -p smart_service_bot < sql/init.sql
```

#### Redis启动

```bash
# Docker方式
docker run -d --name redis \
  -p 6379:6379 \
  redis:7.0-alpine

# 或本地启动
redis-server
```

#### Milvus安装

```bash
# Docker Compose方式（推荐）
cd milvus
docker-compose up -d

# 等待启动完成
docker-compose ps
```

### 3. 后端启动

#### 配置文件

编辑 `backend/src/main/resources/application-dev.yml`:

```yaml
# 数据库配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_service_bot
    username: root
    password: your_password

  # Redis配置
  redis:
    host: localhost
    port: 6379
    password:

# AI模型配置（必填）
ai:
  openai:
    api-key: sk-proj-your-openai-api-key
    base-url: https://api.openai.com/v1
    model: gpt-4

  # 或使用通义千问
  tongyi:
    api-key: your-tongyi-api-key
    model: qwen-turbo

# Milvus配置
milvus:
  host: localhost
  port: 19530
```

#### 启动后端

```bash
cd backend

# Maven方式
mvn clean install
mvn spring-boot:run

# 或使用IDE直接运行 SmartServiceBotApplication.java
```

后端启动成功后访问：
- API文档: http://localhost:8080/doc.html
- 健康检查: http://localhost:8080/actuator/health

### 4. 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 开发模式启动
npm run dev
```

前端启动成功后访问: http://localhost:5173

### 5. 默认账号

```
管理员账号: admin
密码: admin123
```

**⚠️ 生产环境请立即修改默认密码！**

---

## 📦 项目结构

```
smart-service-bot/
├── docs/                           # 项目文档
│   ├── 01-产品需求文档PRD.md       # 产品需求
│   ├── 02-技术方案设计.md          # 技术设计
│   ├── 03-数据库设计.md            # 数据库设计
│   ├── 04-接口规范设计.md          # API规范
│   └── images/                     # 文档图片
│
├── backend/                        # 后端项目
│   ├── pom.xml                    # Maven配置
│   └── src/
│       └── main/
│           ├── java/com/smartbot/
│           │   ├── controller/    # 控制器层
│           │   ├── service/       # 服务层
│           │   ├── dao/           # 数据访问层
│           │   ├── domain/        # 领域模型
│           │   │   ├── entity/    # 实体类
│           │   │   ├── vo/        # 视图对象
│           │   │   ├── dto/       # 数据传输对象
│           │   │   ├── req/       # 请求对象
│           │   │   ├── resp/      # 响应对象
│           │   │   └── query/     # 查询对象
│           │   ├── common/        # 公共组件
│           │   ├── config/        # 配置类
│           │   └── integration/   # 第三方集成
│           └── resources/
│               ├── application.yml
│               └── mapper/        # MyBatis XML
│
├── frontend/                       # 前端项目
│   ├── package.json
│   ├── src/
│   │   ├── views/                 # 页面组件
│   │   ├── components/            # 公共组件
│   │   ├── api/                   # API接口
│   │   ├── stores/                # Pinia状态管理
│   │   ├── router/                # 路由配置
│   │   ├── types/                 # TypeScript类型
│   │   └── utils/                 # 工具函数
│   └── public/
│
├── sql/                           # 数据库脚本
│   ├── init.sql                   # 初始化脚本
│   └── update/                    # 更新脚本
│
├── milvus/                        # Milvus配置
│   ├── docker-compose.yml
│   └── init_collection.py         # 集合初始化
│
├── deploy/                        # 部署配置
│   ├── docker/
│   │   ├── Dockerfile.backend
│   │   ├── Dockerfile.frontend
│   │   └── docker-compose.yml
│   ├── kubernetes/                # K8s配置
│   └── nginx/                     # Nginx配置
│
├── scripts/                       # 脚本工具
│   ├── start.sh                   # 启动脚本
│   ├── stop.sh                    # 停止脚本
│   └── backup.sh                  # 备份脚本
│
├── .gitignore
├── LICENSE
└── README.md
```

---

## 🔧 配置说明

### 核心配置项

#### AI模型配置

系统支持多个AI提供商，在 `application.yml` 中配置：

```yaml
ai:
  # 默认提供商: OPENAI | TONGYI | WENXIN
  default-provider: OPENAI

  # OpenAI配置
  openai:
    api-key: ${OPENAI_API_KEY}
    base-url: https://api.openai.com/v1
    model: gpt-4
    temperature: 0.7
    max-tokens: 2000
    timeout: 30s

  # 通义千问配置
  tongyi:
    api-key: ${TONGYI_API_KEY}
    model: qwen-turbo

  # 向量化配置
  embedding:
    model: text-embedding-ada-002
    dimensions: 1536
```

#### 知识库配置

```yaml
knowledge:
  # 文档上传配置
  upload:
    max-file-size: 10MB
    allowed-types: pdf,doc,docx,txt,md
    storage-path: /data/documents

  # 分块配置
  chunk:
    size: 500              # 每块字符数
    overlap: 50            # 重叠字符数
    enable-ocr: false      # 是否启用OCR

  # RAG检索配置
  rag:
    top-k: 5               # 返回Top-K结果
    similarity-threshold: 0.7  # 相似度阈值
    rerank: true           # 是否重排序
```

#### 缓存配置

```yaml
cache:
  # L1缓存 (Caffeine)
  caffeine:
    context:
      expire-after-write: 30m
      maximum-size: 10000

  # L2缓存 (Redis)
  redis:
    context:
      ttl: 30m
    knowledge:
      ttl: 24h
```

---

## 📚 API文档

### 在线文档

启动后端后访问: http://localhost:8080/doc.html

### 快速示例

#### 1. 用户登录

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

响应:
```json
{
  "code": 200,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 7200
  }
}
```

#### 2. 创建对话

```bash
curl -X POST http://localhost:8080/api/v1/chat/conversations \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "channel": "WEB",
    "userId": 123
  }'
```

#### 3. 发送消息

```bash
curl -X POST http://localhost:8080/api/v1/chat/conversations/{sessionId}/messages \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "如何办理退款？"
  }'
```

更多API详见 [接口规范文档](docs/04-接口规范设计.md)

---

## 🧪 测试

### 单元测试

```bash
cd backend
mvn test
```

### 集成测试

```bash
mvn verify
```

### 前端测试

```bash
cd frontend
npm run test
```

### 测试覆盖率

```bash
mvn clean test jacoco:report
```

查看报告: `backend/target/site/jacoco/index.html`

---

## 📦 部署

### Docker部署（推荐）

#### 1. 构建镜像

```bash
# 构建后端镜像
cd backend
docker build -t smartbot-backend:latest .

# 构建前端镜像
cd frontend
docker build -t smartbot-frontend:latest .
```

#### 2. Docker Compose启动

```bash
cd deploy/docker
docker-compose up -d
```

#### 3. 检查状态

```bash
docker-compose ps
docker-compose logs -f
```

### Kubernetes部署

```bash
cd deploy/kubernetes

# 创建命名空间
kubectl create namespace smartbot

# 部署应用
kubectl apply -f mysql.yaml
kubectl apply -f redis.yaml
kubectl apply -f milvus.yaml
kubectl apply -f backend.yaml
kubectl apply -f frontend.yaml
kubectl apply -f ingress.yaml

# 查看状态
kubectl get pods -n smartbot
```

### 传统部署

#### 后端部署

```bash
# 1. 打包
cd backend
mvn clean package -DskipTests

# 2. 上传jar包到服务器
scp target/smartbot-backend-1.0.0.jar user@server:/app/

# 3. 启动
java -jar /app/smartbot-backend-1.0.0.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

#### 前端部署

```bash
# 1. 构建
cd frontend
npm run build

# 2. 上传dist目录到服务器
scp -r dist/* user@server:/var/www/smartbot/

# 3. 配置Nginx
# 参考 deploy/nginx/nginx.conf
```

---

## 📊 性能指标

### 系统性能

| 指标 | 目标值 | 说明 |
|------|--------|------|
| 并发对话数 | 10,000+ | 同时在线对话数 |
| 响应时间 | <3秒 | P95响应时间 |
| QPS | 1000+ | 每秒查询数 |
| 可用性 | 99.9% | 年度可用性 |

### AI性能

| 指标 | 目标值 | 说明 |
|------|--------|------|
| 答案准确率 | >90% | RAG检索准确率 |
| 知识库命中率 | >75% | 从知识库回答比例 |
| 用户满意度 | >80% | 3分好评率 |
| 平均Token消耗 | <300 | 单次对话Token数 |

### 压测结果

```bash
# 使用JMeter进行压测
cd scripts
./load_test.sh

# 结果示例：
- 1000并发用户
- 平均响应时间: 1.8秒
- P95响应时间: 2.9秒
- P99响应时间: 4.2秒
- 错误率: 0.03%
```

---

## 🛡️ 安全说明

### 安全特性

1. **身份认证**: JWT Token + Sa-Token双重认证
2. **API签名**: HMAC-SHA256签名验证
3. **数据加密**: AES-256加密敏感配置
4. **SQL防注入**: MyBatis参数化查询
5. **XSS防护**: 输入输出过滤
6. **限流保护**: 接口级限流策略
7. **敏感数据脱敏**: 日志自动脱敏

### 安全建议

- ✅ 生产环境必须使用HTTPS
- ✅ 修改默认密码
- ✅ 定期更新依赖版本
- ✅ 开启防火墙，限制端口访问
- ✅ 定期备份数据库
- ✅ 配置日志审计

---

## 🐛 常见问题

### Q1: 启动后端时报错 "Cannot connect to MySQL"

**A**: 检查MySQL是否启动，数据库名、用户名、密码是否正确。

```bash
# 测试MySQL连接
mysql -h localhost -u root -p -e "SHOW DATABASES;"
```

### Q2: AI回复很慢或超时

**A**: 可能原因：
1. OpenAI API网络问题 → 配置代理或使用国内模型
2. Token配额不足 → 检查API Key余额
3. 模型配置不当 → 降低 `max-tokens` 参数

### Q3: 知识库检索不准确

**A**: 优化建议：
1. 调整相似度阈值（降低threshold）
2. 增加Top-K数量
3. 优化文档分块大小
4. 使用更好的Embedding模型

### Q4: 前端无法连接后端

**A**: 检查：
1. 后端是否启动成功
2. 前端 `.env` 文件中的 `VITE_API_BASE_URL` 是否正确
3. 跨域配置是否开启

### Q5: Milvus连接失败

**A**:
```bash
# 检查Milvus状态
docker-compose -f milvus/docker-compose.yml ps

# 重启Milvus
docker-compose -f milvus/docker-compose.yml restart
```

更多问题请查看 [Issues](https://github.com/yourusername/smart-service-bot/issues)

---

## 🗺️ Roadmap

### v1.0.0 (当前版本)
- ✅ 基础对话功能
- ✅ RAG知识库
- ✅ 管理后台
- ✅ Web/API接入

### v1.1.0 (计划中)
- [ ] 微信小程序支持
- [ ] 语音对话能力
- [ ] 多语言支持
- [ ] 对话质量自动评估

### v1.2.0 (未来规划)
- [ ] 智能推荐系统
- [ ] 工单系统集成
- [ ] 客户画像分析
- [ ] A/B测试能力

### v2.0.0 (长期目标)
- [ ] 多租户SaaS化
- [ ] 私有化部署方案
- [ ] 低代码配置能力
- [ ] 行业解决方案

---

## 🤝 贡献指南

我们欢迎所有形式的贡献！

### 贡献方式

1. **提交Issue**: 报告Bug或提出新功能建议
2. **提交PR**: Fork项目，修改代码，提交Pull Request
3. **完善文档**: 改进文档、翻译、示例代码
4. **分享经验**: 撰写技术博客、使用案例

### 开发流程

```bash
# 1. Fork项目到你的GitHub

# 2. 克隆你的Fork
git clone https://github.com/your-username/smart-service-bot.git

# 3. 创建功能分支
git checkout -b feature/your-feature-name

# 4. 提交代码
git add .
git commit -m "feat: add your feature"

# 5. 推送到你的Fork
git push origin feature/your-feature-name

# 6. 在GitHub上创建Pull Request
```

### 代码规范

- **Java**: 遵循阿里巴巴Java开发手册
- **Vue**: 遵循Vue官方风格指南
- **Git Commit**: 使用[约定式提交](https://www.conventionalcommits.org/)

```
feat: 新功能
fix: 修复Bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建/工具相关
```

---

## 📄 License

本项目采用 [MIT License](LICENSE) 开源协议。

```
MIT License

Copyright (c) 2024 smart-service-bot

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

## 💬 社区与支持

### 联系方式

- **GitHub**: https://github.com/yourusername/smart-service-bot
- **Issues**: https://github.com/yourusername/smart-service-bot/issues
- **Discussions**: https://github.com/yourusername/smart-service-bot/discussions
- **Email**: support@smartservicebot.com

### Star History

[![Star History Chart](https://api.star-history.com/svg?repos=yourusername/smart-service-bot&type=Date)](https://star-history.com/#yourusername/smart-service-bot&Date)

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring AI](https://spring.io/projects/spring-ai)
- [Vue.js](https://vuejs.org/)
- [MyBatis Plus](https://baomidou.com/)
- [Milvus](https://milvus.io/)
- [Element Plus](https://element-plus.org/)

---

<div align="center">

**如果这个项目对您有帮助，请给我们一个⭐Star，谢谢！**

Made with ❤️ by smart-service-bot Team

</div>
