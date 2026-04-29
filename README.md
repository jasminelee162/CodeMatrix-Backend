# CodeMatrix Backend

## 项目简介

CodeMatrix 是一个基于 Spring Boot 的代码度量分析后端系统，专门用于分析和评估软件项目的代码质量。该系统集成了多种经典的软件度量方法，包括面向对象度量（CK度量）、功能点分析（FP）、抽象语法树分析（AST）等，为软件开发团队提供全面的代码质量评估和改进建议。

## 主要功能

### 1. 面向对象度量分析 (CK Metrics)
- **Chidamber & Kemerer (CK) 度量套件**
  - WMC (Weighted Methods per Class) - 类的方法权重
  - DIT (Depth of Inheritance Tree) - 继承树深度
  - NOC (Number of Children) - 子类数量
  - CBO (Coupling Between Objects) - 对象间耦合度
  - RFC (Response For a Class) - 类响应度
  - LCOM (Lack of Cohesion in Methods) - 方法内聚性缺失度

### 2. 功能点分析 (Function Point Analysis)
- **UFC (Unadjusted Function Count)** - 未调整功能点计数
- **VAF (Value Adjustment Factor)** - 价值调整因子
- **FP (Function Points)** - 功能点估算

### 3. 代码结构分析
- **AST (Abstract Syntax Tree)** - 抽象语法树分析
  - 类结构分析
  - 方法复杂度分析
  - 代码行数统计
- **圈复杂度 (Cyclomatic Complexity)** - 代码复杂度分析

### 4. 故事点估算 (Story Point Estimation)
- 基于功能点和复杂度的敏捷开发故事点估算

### 5. 用例分析 (Use Case Analysis)
- UML 用例图分析和度量

### 6. AI 辅助分析
- **DeepSeek AI 集成** - 提供智能化的代码质量评价和改进建议
- **指标解读** - 对度量结果进行智能分析和建议

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.3.7
- **语言**: Java 1.8
- **数据库**: MySQL 8.0 + Redis
- **ORM**: MyBatis-Plus 3.4.2
- **API文档**: Swagger 2.9.2
- **代码解析**: JavaParser 3.15.21
- **AI集成**: DeepSeek API

### 核心依赖
```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 代码解析 -->
<dependency>
    <groupId>com.github.javaparser</groupId>
    <artifactId>javaparser-core</artifactId>
    <version>3.15.21</version>
</dependency>

<!-- 数据库 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.2</version>
</dependency>

<!-- AI 集成 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## 项目结构

```
src/main/java/com/csu/ecbackend/
├── controller/          # REST API 控制器
│   ├── AiChatController.java      # AI 对话接口
│   ├── CkController.java          # CK 度量分析
│   ├── FpController.java          # 功能点分析
│   ├── ASTController.java         # AST 分析
│   ├── CCController.java          # 圈复杂度分析
│   └── ...
├── service/            # 业务逻辑层
│   ├── impl/           # 服务实现类
│   ├── AiChatService.java         # AI 服务
│   ├── ASTService.java            # AST 服务
│   ├── LkCkService.java           # CK/LK 服务
│   └── ...
├── bean/               # 数据模型
│   ├── CK.java         # CK 度量结果
│   ├── ASTClass.java   # AST 类信息
│   ├── StoryPoint.java # 故事点
│   └── ...
├── dao/                # 数据访问层
├── util/               # 工具类
├── config/             # 配置类
└── interceptor/        # 拦截器
```

## API 接口

### 基础配置
- **服务端口**: 8080
- **上下文路径**: `/api/`
- **跨域支持**: 已配置 CORS

### 主要接口列表

#### 1. CK 度量分析
- **POST** `/api/ck/ck`
- **参数**: `name` (文件名)
- **返回**: CK 度量结果数组

#### 2. 功能点分析
- **POST** `/api/fp/ufc` - UFC 计算
- **POST** `/api/fp/vaf` - VAF 计算
- **POST** `/api/fp/fp` - FP 计算

#### 3. AST 分析
- **POST** `/api/AST/ASTControl`
- **参数**: `javaFile` (Java文件)
- **返回**: AST 分析结果

#### 4. 圈复杂度分析
- **POST** `/api/CircleComplexity/circleComplexity`
- **参数**: `javaFile` (Java文件)
- **返回**: 复杂度分析结果

#### 5. AI 对话
- **POST** `/api/ai/chat`
- **参数**: `{"prompt": "查询内容"}`
- **返回**: AI 回复

## 数据库配置

```properties
# MySQL 配置
spring.datasource.url=jdbc:mysql://47.101.195.251:3306/redisDemo?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456

# MyBatis-Plus 配置
logging.level.org.csu.mypetstore.api.persistence=trace
```

## AI 配置

```properties
# DeepSeek AI 配置
ai.chat.api.url=https://api.deepseek.com/v1/chat/completions
ai.chat.api.key=sk-8dac650632804c9a8e9213a80144baf0
```

## 运行环境

### 系统要求
- **JDK**: 1.8+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 3.0+

## 开发指南

### 代码规范
- 使用 Lombok 简化代码
- 遵循 RESTful API 设计规范
- 统一使用 `CommonResponse<T>` 作为响应格式

### 扩展开发
1. **添加新度量方法**:
   - 在 `bean/` 下定义数据模型
   - 在 `service/` 下实现业务逻辑
   - 在 `controller/` 下添加 REST 接口

2. **集成新 AI 服务**:
   - 修改 `AiChatService.java` 中的 API 调用逻辑
   - 更新配置文件中的 API 地址和密钥

## 许可证

本项目为课程作业项目，仅供学习和研究使用。

## 贡献者

- 项目开发团队

## 更新日志

### v1.0.0
- 初始版本发布
- 实现基础的代码度量功能
- 集成 AI 辅助分析功能
```