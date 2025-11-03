# smart-service-bot - 后端服务

## 项目简介

smart-service-bot后端服务是基于Spring Boot 3.2 + MyBatis Plus + Spring AI构建的企业级AI客服系统后端，提供RESTful API接口，支持智能对话、知识库管理、RAG检索等核心功能。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Spring Boot | 3.2.0 | 应用框架 |
| Spring AI | 1.0.0-M1 | AI集成框架 |
| MyBatis Plus | 3.5.5 | ORM框架 |
| Sa-Token | 1.37.0 | 权限认证 |
| Knife4j | 4.4.0 | API文档 |
| Lombok | 1.18.30 | 简化开发 |
| Hutool | 5.8.23 | 工具类库 |
| MySQL | 8.0+ | 数据库 |
| Redis | 7.0+ | 缓存 |
| Milvus | 2.3+ | 向量数据库 |

## 项目结构

```
backend/
├── src/main/java/com/smartbot/
│   ├── SmartServiceBotApplication.java   # 启动类
│   ├── controller/                       # 控制器层
│   │   ├── AuthController.java          # 认证控制器
│   │   ├── ChatController.java          # 对话控制器
│   │   ├── KnowledgeController.java     # 知识库控制器（待实现）
│   │   └── AdminController.java         # 管理后台控制器（待实现）
│   ├── service/                          # 服务层
│   │   ├── AuthService.java             # 认证服务接口
│   │   ├── ChatService.java             # 对话服务接口
│   │   ├── RagService.java              # RAG服务接口
│   │   └── impl/                        # 服务实现
│   │       ├── AuthServiceImpl.java
│   │       ├── ChatServiceImpl.java
│   │       └── RagServiceImpl.java
│   ├── dao/                              # 数据访问层
│   │   └── mapper/                      # MyBatis Mapper
│   │       ├── UserMapper.java
│   │       ├── ConversationMapper.java
│   │       ├── MessageMapper.java
│   │       └── KnowledgeDocumentMapper.java
│   ├── domain/                           # 领域模型
│   │   ├── entity/                      # 实体类
│   │   │   ├── User.java
│   │   │   ├── Conversation.java
│   │   │   ├── Message.java
│   │   │   └── KnowledgeDocument.java
│   │   ├── vo/                          # 视图对象
│   │   │   ├── MessageVO.java
│   │   │   └── ConversationVO.java
│   │   ├── req/                         # 请求对象
│   │   │   ├── LoginReq.java
│   │   │   ├── SendMessageReq.java
│   │   │   └── CreateConversationReq.java
│   │   ├── resp/                        # 响应对象
│   │   │   └── LoginResp.java
│   │   └── query/                       # 查询对象
│   │       ├── PageQuery.java
│   │       └── ConversationQuery.java
│   ├── common/                           # 公共组件
│   │   ├── enums/                       # 枚举类
│   │   │   ├── UserRole.java
│   │   │   ├── ConversationStatus.java
│   │   │   ├── MessageRole.java
│   │   │   └── IntentType.java
│   │   ├── exception/                   # 异常处理
│   │   │   ├── BusinessException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   └── result/                      # 统一响应
│   │       ├── Result.java
│   │       └── PageResp.java
│   ├── config/                           # 配置类
│   │   ├── MyBatisPlusConfig.java       # MyBatis Plus配置
│   │   ├── CorsConfig.java              # 跨域配置
│   │   └── SwaggerConfig.java           # API文档配置
│   └── integration/                      # 第三方集成（待实现）
│       ├── openai/                      # OpenAI集成
│       └── milvus/                      # Milvus集成
├── src/main/resources/
│   ├── application.yml                   # 主配置文件
│   ├── application-dev.yml               # 开发环境配置
│   ├── application-prod.yml              # 生产环境配置
│   └── mapper/                          # MyBatis XML映射文件
├── pom.xml                               # Maven配置
└── README.md                             # 本文档
```

## 快速开始

### 1. 环境准备

确保已安装：
- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+
- Milvus 2.3+ (可选，知识库功能需要)

### 2. 数据库初始化

```bash
# 创建数据库
mysql -u root -p

CREATE DATABASE smart_service_bot DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入初始化脚本
mysql -u root -p smart_service_bot < ../sql/init.sql
```

### 3. 配置文件

编辑 `src/main/resources/application-dev.yml`:

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

# AI配置（必填）
ai:
  openai:
    api-key: sk-proj-your-openai-api-key-here
    base-url: https://api.openai.com/v1
```

### 4. 启动项目

#### 方式一：使用Maven

```bash
# 编译打包
mvn clean package -DskipTests

# 启动应用
java -jar target/smart-service-bot.jar --spring.profiles.active=dev

# 或直接运行
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### 方式二：使用IDE

直接运行 `SmartServiceBotApplication.java`

### 5. 验证启动

启动成功后，访问：

- **API文档**: http://localhost:8080/doc.html
- **健康检查**: http://localhost:8080/actuator/health

看到Knife4j API文档界面即表示启动成功！

## API接口

### 认证接口

#### 用户登录

```bash
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

响应：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "accessToken": "...",
    "expiresIn": 7200,
    "tokenType": "Bearer",
    "userInfo": {
      "userId": 1,
      "username": "admin",
      "nickname": "管理员",
      "role": "ADMIN"
    }
  }
}
```

### 对话接口

#### 创建会话

```bash
POST /api/v1/chat/conversations
Authorization: Bearer {token}
Content-Type: application/json

{
  "channel": "WEB",
  "userId": 123
}
```

#### 发送消息

```bash
POST /api/v1/chat/conversations/{sessionId}/messages
Authorization: Bearer {token}
Content-Type: application/json

{
  "content": "如何办理退款？",
  "messageType": "TEXT"
}
```

#### 获取会话历史

```bash
GET /api/v1/chat/conversations/{sessionId}/messages?pageNum=1&pageSize=20
Authorization: Bearer {token}
```

更多API详见：http://localhost:8080/doc.html

## 核心功能实现

### 已实现功能

- ✅ 用户认证（登录/登出）
- ✅ 会话管理（创建/关闭）
- ✅ 消息发送与接收
- ✅ 会话历史查询
- ✅ 统一异常处理
- ✅ 统一响应格式
- ✅ API文档（Knife4j）
- ✅ 跨域配置
- ✅ MyBatis Plus分页

### 待实现功能

- ⏳ RAG知识库检索（当前为模拟实现）
- ⏳ AI模型集成（OpenAI/通义千问）
- ⏳ 向量数据库集成（Milvus）
- ⏳ 意图识别
- ⏳ 知识库管理
- ⏳ 管理后台接口
- ⏳ WebSocket实时通信
- ⏳ 消息评价功能

## 数据库设计

### 核心表结构

```sql
-- 用户表
CREATE TABLE `user` (
  `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(100),
  `role` VARCHAR(20) DEFAULT 'NORMAL',
  `status` VARCHAR(20) DEFAULT 'ENABLED',
  ...
);

-- 会话表
CREATE TABLE `conversation` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `session_id` VARCHAR(100) NOT NULL UNIQUE,
  `user_id` BIGINT,
  `channel` VARCHAR(20),
  `status` VARCHAR(20) DEFAULT 'ACTIVE',
  ...
);

-- 消息表
CREATE TABLE `message` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `message_id` VARCHAR(100) NOT NULL UNIQUE,
  `session_id` VARCHAR(100) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  `content` TEXT,
  ...
);
```

完整数据库设计见：`../docs/03-数据库设计.md`

## 开发规范

### 代码规范

1. **实体类命名**: 使用 `Entity + VO + DTO + Req + Resp + Query` 模式
2. **Service接口**: 接口与实现分离，实现类放在 `impl` 包下
3. **异常处理**: 业务异常使用 `BusinessException`，全局异常使用 `GlobalExceptionHandler`
4. **日志规范**: 关键操作必须记录日志
5. **注释规范**: 类、方法必须添加JavaDoc注释

### 分层职责

| 层次 | 职责 | 规范 |
|------|------|------|
| Controller | 接收请求、参数校验、返回响应 | 不包含业务逻辑 |
| Service | 业务逻辑处理 | 核心业务代码 |
| Mapper | 数据访问 | 仅数据库操作 |
| Domain | 领域模型 | Entity/VO/DTO/Req/Resp/Query |

### Git提交规范

```
feat: 新功能
fix: Bug修复
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建/工具相关
```

## 测试

### 单元测试

```bash
mvn test
```

### 集成测试

```bash
mvn verify
```

### 测试覆盖率

```bash
mvn clean test jacoco:report
```

查看报告：`target/site/jacoco/index.html`

## 部署

### 打包

```bash
mvn clean package -DskipTests
```

生成文件：`target/smart-service-bot.jar`

### 运行

```bash
# 开发环境
java -jar smart-service-bot.jar --spring.profiles.active=dev

# 生产环境
java -jar smart-service-bot.jar --spring.profiles.active=prod \
  -Xms2g -Xmx2g \
  -XX:+UseG1GC
```

### Docker部署

```bash
# 构建镜像
docker build -t smartbot-backend:latest .

# 运行容器
docker run -d \
  --name smartbot-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e MYSQL_HOST=mysql \
  -e REDIS_HOST=redis \
  smartbot-backend:latest
```

## 配置说明

### 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| SPRING_PROFILES_ACTIVE | 环境配置 | dev |
| MYSQL_HOST | MySQL主机 | localhost |
| MYSQL_PORT | MySQL端口 | 3306 |
| MYSQL_DATABASE | 数据库名 | smart_service_bot |
| MYSQL_USERNAME | 数据库用户名 | root |
| MYSQL_PASSWORD | 数据库密码 | - |
| REDIS_HOST | Redis主机 | localhost |
| REDIS_PORT | Redis端口 | 6379 |
| OPENAI_API_KEY | OpenAI API Key | - |
| MILVUS_HOST | Milvus主机 | localhost |

### 核心配置项

详见 `application.yml`

## 常见问题

### Q1: 启动时报错 "Cannot load driver class: com.mysql.cj.jdbc.Driver"

**A**: MySQL驱动未正确加载，检查：
1. pom.xml中是否包含mysql-connector-j依赖
2. 运行 `mvn clean install` 重新安装依赖

### Q2: 登录接口返回500错误

**A**: 可能原因：
1. 数据库未初始化 → 执行 `init.sql`
2. 用户表无数据 → 手动插入admin用户
3. 密码加密问题 → 使用BCrypt加密

### Q3: Knife4j文档访问404

**A**: 检查：
1. `knife4j.enable` 是否为true
2. 访问地址是否正确：http://localhost:8080/doc.html
3. Spring Boot版本是否为3.x（需要knife4j 4.x版本）

### Q4: 跨域问题

**A**: 已配置 `CorsConfig`，如仍有问题：
1. 检查前端请求是否携带正确的Origin头
2. 检查是否有其他拦截器影响

## 性能优化

### 已实现的优化

1. **数据库连接池**: 使用HikariCP（Spring Boot默认）
2. **MyBatis缓存**: 开启一级缓存
3. **分页查询**: 使用MyBatis Plus分页插件
4. **异步处理**: 启用@EnableAsync（待用）

### 待优化项

1. **多级缓存**: Caffeine + Redis
2. **数据库索引**: 根据查询优化索引
3. **连接池调优**: 根据负载调整参数
4. **JVM调优**: G1GC参数优化

## 监控与日志

### 日志配置

日志文件：`logs/smart-service-bot.log`

日志级别：
- 开发环境：DEBUG
- 生产环境：INFO

### 监控端点

Actuator监控端点：

- 健康检查：http://localhost:8080/actuator/health
- 指标统计：http://localhost:8080/actuator/metrics
- Prometheus：http://localhost:8080/actuator/prometheus

## 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork项目
2. 创建功能分支：`git checkout -b feature/your-feature`
3. 提交代码：`git commit -m "feat: add your feature"`
4. 推送分支：`git push origin feature/your-feature`
5. 创建Pull Request

## 许可证

本项目采用 MIT License 开源协议。

---

**技术支持**: support@smartservicebot.com

**项目地址**: https://github.com/yourusername/smart-service-bot
