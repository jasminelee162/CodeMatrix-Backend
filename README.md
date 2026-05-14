# CodeMatrix Backend

一个综合性的软件度量与代码质量分析平台后端服务，提供多维度的代码度量、AI 智能分析与优化建议。

**前端仓库：** [CodeMatrix-Vue3](https://github.com/jasminelee162/CodeMatrix-Vue3)

---

## 项目概述

CodeMatrix-Backend 是 CodeMatrix 平台的核心服务端，致力于提供全面的代码质量评估与优化方案。通过多种度量算法与 AI 赋能，帮助开发团队快速识别代码问题、理解代码结构、优化代码质量。

### 核心特性

- 多维度度量：功能点度量、用例点度量、面向对象度量、代码行数度量
- UML 解析：支持 XMI 格式 UML 类图的自动解析与结构分析
- 代码质量指标：CK 度量（DIT、NOC、CBO、WMC、RFC、LCOM）
- AI 智能分析（核心亮点）：
  - 基于 DeepSeek 大模型的度量评审
  - 自动生成结构化评估报告
  - 提供可执行的代码优化建议
  - 实时对话式代码质量咨询
- AI 驱动的结构优化：
  - 自动生成优化后的 UML/XMI 代码
  - 基于 CK 度量的耦合度降低方案
  - 内聚性改善建议
  - 继承深度优化策略
- 统一响应格式，易于前端集成

---

## 主要功能

### 1. 面向对象度量分析 (CK Metrics)
- WMC (Weighted Methods per Class) - 加权方法数
- DIT (Depth of Inheritance Tree) - 继承树深度
- NOC (Number of Children) - 子类数量
- CBO (Coupling Between Objects) - 对象间耦合度
- RFC (Response For a Class) - 响应集大小
- LCOM (Lack of Cohesion in Methods) - 方法内聚性缺失度

### 2. 功能点分析 (Function Point Analysis)
- UFC (Unadjusted Function Count) - 未调整功能点计数
- VAF (Value Adjustment Factor) - 价值调整因子
- FP (Function Points) - 功能点估算

### 3. 代码行度量
- 空行、注释行、代码行统计
- 物理代码行（总行数）、逻辑代码行

### 4. 用例点度量
- UML 用例图分析与点值计算
- 参与者及用例提取、复杂度评估

### 5. AI 解析与优化

#### 5.1 AI 度量评审
- 基于输入的 CK 度量值进行智能分析
- 返回 JSON 格式的结构化评审结果（整体评价、风险等级、发现、建议）
- 接口：`POST /ai/metric-review`

#### 5.2 AI 自由对话
- 支持关于度量结果、代码质量、架构设计的任意问题
- DeepSeek 大模型驱动的自然语言对话
- 接口：`POST /ai/chat`

#### 5.3 AI 代码自动优化
- 输入 UML/XMI，输出优化后的 XML + 详细说明
- 基于 CK 度量优化目标（降低耦合度、提升内聚性、控制继承深度等）
- 接口：`POST /ai/optimize`

### 6. 故事点估算
- 基于功能点和复杂度的敏捷开发故事点估算

---

## 系统架构

### 功能需求图

[请在此处插入功能需求图]

### 软件体系结构说明图

[请在此处插入软件体系结构说明图]

### 功能模块分解图

[请在此处插入功能模块分解图]

### 核心功能流程

```
用户上传文件 (Java/UML)
    │
    ▼
文件解析与验证
    │
    ├─ CK 度量 (DIT/NOC/CBO/WMC/RFC/LCOM)
    ├─ FP 分析 (UFC/VAF)
    ├─ UC 点计算
    ├─ 代码行统计
    │
    ▼
度量结果输出
    │
    ├─ 直接返回给前端
    └─ AI 分析评审 (可选)
       ├─ 度量评审
       ├─ 架构优化建议
       └─ 自动优化方案生成
```

---

## 快速开始

### 环境要求

- Java 1.8+
- Spring Boot 2.3.7
- MySQL 5.7+
- Maven 3.6+

### 配置

编辑 `src/main/resources/application.properties`：

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/codematrix?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password

# AI Config
ai.chat.api.url=https://api.deepseek.com/v1/chat/completions
ai.chat.api.key=your_api_key_here

# Logging
logging.level.root=INFO
logging.level.com.csu.ecbackend=DEBUG
```

### 构建与运行

```bash
# 克隆项目
git clone https://github.com/jasminelee162/CodeMatrix-Backend.git
cd CodeMatrix-Backend

# 构建
mvn clean install

# 运行
mvn spring-boot:run
```

启动后访问：
- API 文档：http://localhost:8080/swagger-ui.html
- 健康检查：http://localhost:8080/actuator/health

---

## API 端点

### 度量接口

**FP 度量**
```http
POST /fp/fp
参数：name=文件名
```

**用例点度量**
```http
POST /uc/useCase
参数：file=UML用例文件
```

**CK 度量**
```http
POST /ck/ck
参数：name=UML类图文件名
返回：[{name, dit, noc, cbo, wmc, rfc, lcom}]
```

**代码行度量**
```http
POST /CodeLines/codeLine
参数：javaFile=Java源文件
返回：{nullLines, annoLines, codeLines, allLines, logicLines}
```

### AI 服务接口

**AI 自由对话**
```http
POST /ai/chat
Content-Type: application/json

{
  "prompt": "你的问题"
}
```

**AI 度量评审**
```http
POST /ai/metric-review
Content-Type: application/json

{
  "projectName": "项目名",
  "metrics": {
    "DIT": 3.0,
    "NOC": 2.0,
    "CBO": 5.0,
    "WMC": 12.0,
    "RFC": 20.0,
    "LCOM": 4.0
  }
}

返回：{
  "overallAssessment": "评价",
  "riskLevel": "风险等级",
  "keyFindings": ["发现1"],
  "suggestions": ["建议1"]
}
```

**AI 代码优化**
```http
POST /ai/optimize
Content-Type: application/json

{
  "code": "<?xml version=\"1.0\"?>... XML 代码"
}

返回：{
  "code": "优化后的 XML",
  "explanation": "优化说明"
}
```

---

## 项目结构

```
src/main/java/com/csu/ecbackend/
├── controller/              # REST API 控制器
│   ├── AiChatController.java
│   ├── CkController.java
│   ├── CodeLinesController.java
│   └── ...
├── service/                 # 业务逻辑
│   ├── AiChatService.java
│   ├── LkCkService.java
│   └── ...
├── service/impl/            # 服务实现
├── bean/                    # 数据模型
├── vo/                      # 数据传输对象
├── dao/                     # 数据访问层
├── common/                  # 通用类
└── util/                    # 工具类
```

---

## 关键技术方案

### UML/XMI 解析

使用 dom4j 库解析 XML 格式的 UML 文件，提取类、方法、参数等信息。

### CK 度量计算

基于解析的 UML 结构，计算六项指标：
- DIT：继承链深度
- NOC：直接子类计数
- CBO：关联耦合数
- WMC：方法数（排除 getter/setter）
- RFC：响应集大小
- LCOM：内聚性缺失度

### AI 集成

使用 RestTemplate 调用 DeepSeek API，支持：
- 自然语言对话
- 度量结果分析
- 代码优化建议
- 结果容错处理

---

## 测试

### 单元测试
- 度量计算逻辑验证
- 代码行统计准确性
- UML 解析正确性

### 集成测试
- API 端点调用
- AI 服务稳定性
- 端到端流程

---

## 常见问题

**Q: AI 返回"余额不足"错误？**
检查 DeepSeek API Key 是否有效、账户余额是否充足。

**Q: UML 文件无法解析？**
确保上传标准 XMI 格式文件，包含必要的 uml:Class、generalization、ownedAttribute 等节点。

**Q: 度量结果与预期不符？**
检查输入文件格式、UML 结构完整性。

**Q: 服务启动失败？**
检查数据库连接、API Key 配置、日志错误。

---

## 与前端集成

前端项目：[CodeMatrix-Vue3](https://github.com/jasminelee162/CodeMatrix-Vue3)

### 集成要点
- CORS 已配置，支持跨域请求
- 所有接口返回统一的 `CommonResponse<T>` 格式
- 文件上传使用 `multipart/form-data`
- 响应状态码：200 成功，其他为失败

### 前端交互示例

```javascript
// AI 度量评审
fetch('http://localhost:8080/ai/metric-review', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    projectName: '示例',
    metrics: { DIT: 3.0, CBO: 5.0 }
  })
});

// 代码行度量
const formData = new FormData();
formData.append('javaFile', file);
fetch('http://localhost:8080/CodeLines/codeLine', {
  method: 'POST',
  body: formData
});
```

---

## 许可证

MIT License

---

## 联系方式

GitHub Issues: [报告问题](https://github.com/jasminelee162/CodeMatrix-Backend/issues)
- `spring-boot-starter-data-jpa` - 数据层框架
- `mybatis-plus` - ORM 增强
- `lombok` - 代码生成
- `swagger` - API 文档
- `jackson` - JSON 处理
- `commons-lang3` - 工具库
- `dom4j` - XML 解析

### 配置文件

编辑 `src/main/resources/application.properties`：

```properties
# Server Config
server.port=8080

# Database Config
spring.datasource.url=jdbc:mysql://localhost:3306/codematrix?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# AI Chat Config
ai.chat.api.url=https://api.deepseek.com/v1/chat/completions
ai.chat.api.key=your_api_key_here

# Logging
logging.level.root=INFO
logging.level.com.csu.ecbackend=DEBUG
```

### 构建与运行

```bash
# 克隆项目
git clone https://github.com/jasminelee162/CodeMatrix-Backend.git
cd CodeMatrix-Backend

# 构建项目
mvn clean install

# 运行服务
mvn spring-boot:run

# 或使用 JAR 文件
java -jar target/ecbackend.jar
```

服务启动后访问：

- **API 文档**：http://localhost:8080/swagger-ui.html
- **健康检查**：http://localhost:8080/actuator/health

---

##  API 端点详解

### 度量相关接口

#### 1️⃣ FP 度量接口

```http
POST /fp/fp
Content-Type: application/x-www-form-urlencoded

参数：name=文件名
返回：{ "nullLines", "annoLines", "codeLines", "allLines", "logicLines" }
```

#### 2️⃣ 用例点度量接口

```http
POST /uc/useCase
Content-Type: multipart/form-data

参数：file=UML用例文件
返回：用例点度量结果
```

#### 3️⃣ CK 度量接口

```http
POST /ck/ck
Content-Type: application/x-www-form-urlencoded

参数：name=UML类图文件名
返回：[
  {
    "name": "类名",
    "dit": 继承深度,
    "noc": 子类数,
    "cbo": 耦合度,
    "wmc": 加权方法数,
    "rfc": 响应集大小,
    "lcom": 内聚性缺失度
  }
]
```

#### 4️⃣ 代码行度量接口

```http
POST /CodeLines/codeLine
Content-Type: multipart/form-data

参数：javaFile=Java源文件
返回：{
  "nullLines": 空行数,
  "annoLines": 注释行数,
  "codeLines": 代码行数,
  "allLines": 总行数,
  "logicLines": 逻辑行数
}
```

###  AI 服务接口（核心功能）

#### 5️⃣ AI 自由对话接口 

```http
POST /ai/chat
Content-Type: application/json

请求体：
{
  "prompt": "询问任何关于代码质量、架构设计的问题"
}

响应体：
{
  "code": 200,
  "message": "success",
  "data": {
    "answer": "AI 基于 DeepSeek 模型的智能回答"
  }
}
```

**使用场景**：
- 理解 CK 度量指标的含义
- 学习代码重构最佳实践
- 获取架构设计建议

#### 6️⃣ AI 度量评审接口  （智能分析）

```http
POST /ai/metric-review
Content-Type: application/json

请求体：
{
  "projectName": "项目名称",
  "metricSystem": "CK度量",
  "context": "可选的评审背景信息",
  "metrics": {
    "DIT": 3.0,
    "NOC": 2.0,
    "CBO": 5.0,
    "WMC": 12.0,
    "RFC": 20.0,
    "LCOM": 4.0
  }
}

响应体：
{
  "code": 200,
  "message": "success",
  "data": {
    "overallAssessment": "整体评价（中文文本）",
    "riskLevel": "风险等级（高/中/低）",
    "keyFindings": ["发现1", "发现2", "发现3"],
    "suggestions": ["建议1", "建议2", "建议3"],
    "rawAnswer": "AI 原始回答"
  }
}
```

**工作流程**：
1. 输入 6 大 CK 度量值
2. AI 基于指标数据进行智能分析
3. 生成结构化评估报告（JSON 格式）
4. 识别主要问题和改进方向

**使用场景**：
- 快速获取代码质量现状评估
- 理解各指标数值的含义与风险
- 获得优先级改进建议
- 团队会议中讨论代码质量

#### 7️⃣ AI 代码自动优化接口  （自动生成优化方案）

```http
POST /ai/optimize
Content-Type: application/json

请求体：
{
  "code": "<?xml version=\"1.0\"?>\n<uml:Model ...>\n  <!-- UML/XMI 类图 XML 内容 -->\n</uml:Model>"
}

响应体：
{
  "code": 200,
  "message": "success",
  "data": {
    "code": "<?xml version=\"1.0\"?>\n<uml:Model ...>\n  <!-- 优化后的 XML 代码 -->\n</uml:Model>",
    "explanation": "优化说明（中文）\n\n1. 降低耦合度 (CBO)...\n2. 改善内聚性 (LCOM)...\n3. 优化继承深度 (DIT)...\n..."
  }
}
```

**核心特性**：
-  **保持结构完整**：原始 `xmi:id` 和命名空间不变
-  **智能优化**：基于 CK 度量六大指标
-  **可执行性强**：返回合法的 XML 代码
-  **详细说明**：中文逐条优化说明

**优化目标**：

| 指标 | 优化方向 | 具体操作 |
|------|---------|---------|
| **CBO** |  降低耦合 | 减少类间关联、解耦依赖 |
| **LCOM** | 提升内聚 | 增强方法聚集度、改善职责分离 |
| **DIT** |  控制深度 | 避免过深继承链、使用接口替代 |
| **WMC** |  优化方法 | 合理分解方法、避免方法臃肿 |

**使用场景**：
-  快速获得重构方案（秒级响应）
-  学习面向对象设计最佳实践
-  指导团队代码重构方向
-  探索不同的架构设计方案

**工作流程**：

```
输入 UML/XMI XML
    │
    ▼
AI 分析当前结构
    │
    ├─► 识别耦合点
    ├─► 分析内聚性
    ├─► 检查继承深度
    └─► 评估方法数量
    │
    ▼
生成优化方案
    │
    ├─► 修改关联关系
    ├─► 调整类结构
    ├─► 重组继承体系
    └─► 分解方法职责
    │
    ▼
输出优化后的 XML + 说明
```

---

##  项目结构

```
CodeMatrix-Backend/
├── src/
│   ├── main/
│   │   ├── java/com/csu/ecbackend/
│   │   │   ├── controller/              # 请求处理控制层
│   │   │   │   ├── AiChatController.java      # AI 服务
│   │   │   │   ├── AiOptimizeController.java  # AI 优化
│   │   │   │   ├── CkController.java         # CK 度量
│   │   │   │   ├── CodeLinesController.java  # 代码行度量
│   │   │   │   ├── FpController.java         # 功能点度量
│   │   │   │   ├── UCController.java         # 用例点度量
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── service/                 # 业务逻辑服务层
│   │   │   │   ├── AiChatService.java        # AI 度量评审
│   │   │   │   ├── CodeLinesService.java     # 代码行服务接口
│   │   │   │   ├── LkCkService.java          # CK/LK 服务接口
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── service/impl/            # 服务实现
│   │   │   │   ├── CodeLineServiceImpl.java   # 代码行统计实现
│   │   │   │   ├── LkCkServiceImpl.java       # CK 度量实现
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── bean/                    # 数据模型类
│   │   │   │   ├── CK.java              # CK 度量结果
│   │   │   │   ├── CodeLines.java       # 代码行统计结果
│   │   │   │   ├── Class.java           # UML 类
│   │   │   │   ├── Operation.java       # UML 方法
│   │   │   │   ├── Parameter.java       # UML 参数
│   │   │   │   ├── Association.java     # UML 关联
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── vo/                      # 数据传输对象
│   │   │   │   ├── AiChatRequest.java
│   │   │   │   ├── AiChatResponse.java
│   │   │   │   ├── AiMetricReviewRequest.java
│   │   │   │   ├── AiMetricReviewResponse.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── dao/                     # 数据访问层
│   │   │   ├── persistence/             # 持久化配置
│   │   │   ├── util/                    # 工具类集合
│   │   │   ├── common/                  # 通用类
│   │   │   │   ├── CommonResponse.java  # 统一响应格式
│   │   │   │   ├── ResponseCode.java    # 响应状态码
│   │   │   │   └── ...
│   │   │   ├── readData/                # 文件读取模块
│   │   │   ├── tzqServer/               # 度量计算模块
│   │   │   └── EcBackendApplication.java # 启动类
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── logback.xml
│   │       └── hanlp.properties
│   │
│   └── test/
│       └── java/com/csu/ecbackend/
│           └── EcBackendApplicationTests.java
│
├── pom.xml                              # Maven 配置
├── mvnw / mvnw.cmd                     # Maven 包装脚本
└── README.md                            # 项目文档
```

---

##  实验指南

本项目包含五个主要实验部分，用于验证各度量模块的正确性和完整性。

### 4.1 功能点度量实验

- **目标**：验证功能点分解与计算逻辑
- **输入**：需求规格说明文档
- **验证**：FP 值计算与复杂度评估

### 4.2 用例点度量实验

- **目标**：验证用例点的统计与加权计算
- **输入**：用例模型（UML 用例图）
- **验证**：用例参与者和复杂度因子的正确识别

### 4.3 面向对象度量实验

- **目标**：验证 CK 度量指标计算正确性
- **输入**：UML/XMI 格式的类图文件
- **验证**：DIT、NOC、CBO、WMC、RFC、LCOM 六项指标
- **关键代码**：
  - `src/main/java/com/csu/ecbackend/service/impl/LkCkServiceImpl.java`
  - `src/main/java/com/csu/ecbackend/controller/CkController.java`

### 4.4 代码行度量实验

- **目标**：验证代码行统计的准确性
- **输入**：Java 源代码文件
- **验证**：空行、注释行、代码行、物理行、逻辑行的统计
- **关键代码**：
  - `src/main/java/com/csu/ecbackend/service/impl/CodeLineServiceImpl.java`
  - `src/main/java/com/csu/ecbackend/controller/CodeLinesController.java`

### 5.  AI 解析与优化实验（项目创新点）

- **目标**：验证 AI 服务的稳定性和优化建议的质量
- **输入**：
  - 度量指标集合（DIT、NOC、CBO、WMC、RFC、LCOM）
  - UML/XMI 代码片段
- **验证**：
  - AI 评审的结构化输出是否准确
  - 优化建议是否具有可执行性
  - 生成的 XML 是否保持合法性
- **关键代码**：
  - `src/main/java/com/csu/ecbackend/service/AiChatService.java` - AI 服务核心
  - `src/main/java/com/csu/ecbackend/controller/AiChatController.java` - AI 对话接口
  - `src/main/java/com/csu/ecbackend/controller/AiOptimizeController.java` - 代码优化接口
- **实验流程**：
  1. 准备测试用例（高耦合/低内聚类结构）
  2. 调用 `/ai/metric-review` 获取 AI 评审
  3. 调用 `/ai/optimize` 获取自动优化方案
  4. 对比优化前后的度量指标变化
  5. 评估 AI 建议的可行性和改进效果

---

##  核心类说明

###  AI 相关类（核心特色）

| 类名 | 功能 | 位置 |
|------|------|------|
| **AiChatService** | AI 对话、度量评审、提示词构建、结果解析 | `service/` |
| **AiChatController** | AI 对话接口、度量评审接口 | `controller/` |
| **AiOptimizeController** | AI 代码优化接口、XML 处理 | `controller/` |
| **AiChatRequest** | AI 对话请求数据 | `vo/` |
| **AiChatResponse** | AI 对话响应数据 | `vo/` |
| **AiMetricReviewRequest** | AI 度量评审请求 | `vo/` |
| **AiMetricReviewResponse** | AI 度量评审响应 | `vo/` |

### 度量相关类

| 类名 | 功能 | 位置 |
|------|------|------|
| `LkCkServiceImpl` | CK 度量计算（DIT、NOC、CBO、WMC、RFC、LCOM） | `service/impl/` |
| `CodeLineServiceImpl` | 代码行统计 | `service/impl/` |
| `Class` / `Operation` / `Parameter` | UML 类结构数据模型 | `bean/` |
| `CK` / `LK` | 度量结果对象 | `bean/` |
| `CodeLines` | 代码行度量结果 | `bean/` |

### 通用基础类

| 类名 | 功能 | 位置 |
|------|------|------|
| **CommonResponse<T>** | 统一响应格式（所有接口返回） | `common/` |
| **ResponseCode** | 响应状态码枚举 | `common/` |

### AI 相关类

| 类名 | 功能 | 位置 |
|------|------|------|
| `AiChatService` | AI 对话与度量评审 | `service/` |
| `AiOptimizeController` | AI 优化代码接口 | `controller/` |
| `AiMetricReviewRequest` | AI 评审请求 | `vo/` |
| `AiMetricReviewResponse` | AI 评审响应 | `vo/` |

### 通用类

| 类名 | 功能 | 位置 |
|------|------|------|
| `CommonResponse<T>` | 统一响应格式 | `common/` |
| `ResponseCode` | 响应状态码枚举 | `common/` |

---

## 🔧 关键技术方案

### 1. UML/XMI 解析

使用 **dom4j** 库解析 XML 格式的 UML 文件：

```java
// 示例：解析类定义
Document document = saxReader.read(new File(filePath));
Element root = document.getRootElement();
List elements = root.elements();
// 迭代获取 uml:Class、generalization、ownedAttribute、ownedOperation 等
```

### 2. CK 度量计算

基于解析的 UML 结构，逐个计算六项指标：

- **DIT**：继承链深度，循环向上遍历父类
- **NOC**：直接子类计数，扫描所有类的 parent 属性
- **CBO**：关联耦合，统计 Association 出现次数
- **WMC**：方法数，排除 getter/setter 方法
- **RFC**：响应集大小，本类方法 + 参数引用类的方法（去重）
- **LCOM**：内聚性缺失度，基于方法参数类型交集计算

### 3. AI 集成（核心创新点）

#### 3.1 DeepSeek API 调用

使用 **RestTemplate** 调用 DeepSeek API，完整流程如下：

```java
// 构建请求头
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.setBearerAuth(apiKey);

// 构建请求体（包含提示词）
Map<String, Object> body = new HashMap<>();
body.put("model", "deepseek-chat");
body.put("messages", messages);  // [{"role":"user", "content":"..."}]
body.put("temperature", temperature);  // 0.2 (低) 或 0.7 (高)

// 发送请求
ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

// 解析 JSON 响应
JsonNode root = objectMapper.readTree(response.getBody());
String answer = root.path("choices").path(0).path("message").path("content").asText();
```

#### 3.2 AI 提示词工程

针对不同场景设计专门的提示词（Prompt）：

**度量评审提示词示例**：

```
你是一名资深软件度量与架构评审顾问。请基于输入的 CK 度量指标，进行客观评价并给出可执行改进建议。
必须返回严格 JSON 格式，包含以下字段：
- overallAssessment: 整体评价
- riskLevel: 风险等级
- keyFindings: 关键发现数组
- suggestions: 改进建议数组
```

**代码优化提示词示例**：

```
你是一个面向对象软件质量优化专家。下面是一个 UML XMI 格式的 XML 文件，描述了类结构。
请根据 CK 度量标准（WMC、RFC、DIT、NOC、LCOM、CBO）进行优化，目标是：
- 降低耦合度（CBO）：减少不必要的关联
- 减少内聚性缺失（LCOM）：让方法使用更多共同参数
- 控制继承深度（DIT）：避免过深的继承链

重要约束：
1. 所有元素的 xmi:id 属性值必须保持不变
2. generalization 的 general 属性必须引用已存在的 xmi:id
3. 输出必须是合法的 XML
4. 不要添加任何 XML 注释

请按格式输出：
第一部分：优化后的完整 XML 内容
分隔符：===EXPLANATION===
第二部分：中文逐条说明优化理由
```

#### 3.3 AI 结果处理与容错

系统对 AI 返回结果的处理包括：

```java
// 场景 1：成功解析 JSON 响应
if (response.isSuccess()) {
    JsonNode root = objectMapper.readTree(answer);
    response.setOverallAssessment(root.path("overallAssessment").asText());
    response.setRiskLevel(root.path("riskLevel").asText());
    // ... 解析其他字段
}

// 场景 2：AI 返回非标准 JSON（容错）
} else {
    response.setRawAnswer(answer);  // 保存原始回答
    response.setOverallAssessment(answer);  // 作为评价文本
    response.setRiskLevel("未知");
    response.setKeyFindings(Arrays.asList("AI 返回结果已保留"));
}

// 场景 3：API 余额不足或配额超限
} catch (HttpClientErrorException e) {
    if (e.getResponseBodyAsString().contains("balance|quota")) {
        return "抱歉，AI 服务当前不可用（账户余额不足）";
    }
}
```

#### 3.4 AI 输出后处理

对 AI 返回的代码进行清理：

```java
// 去掉 Markdown 代码块语法
String stripMarkdownFence(String text) {
    String s = text.trim();
    if (s.startsWith("```")) {
        // 移除开始和结束的 ``` 标记
        s = s.substring(s.indexOf('\n') + 1, s.lastIndexOf("```"));
    }
    // 查找第一个 XML 标签位置
    int xmlStart = s.indexOf("<?xml");
    if (xmlStart < 0) xmlStart = s.indexOf("<");
    if (xmlStart > 0) s = s.substring(xmlStart);
    return s.trim();
}

// 按分隔符分离代码和说明
int sepIdx = fullContent.indexOf("===EXPLANATION===");
if (sepIdx >= 0) {
    optimizedCode = fullContent.substring(0, sepIdx).trim();
    explanation = fullContent.substring(sepIdx + separator.length()).trim();
}
```

### 4. 统一响应格式

所有接口返回 `CommonResponse<T>` 对象，确保前后端数据结构一致：

```java
public class CommonResponse<T> {
    private int code;           // 200 表示成功，其他为失败
    private String message;     // 状态描述消息
    private T data;             // 实际业务数据（度量结果、AI 回答等）
    
    // 工厂方法
    public static <T> CommonResponse<T> createForSuccess(String message, T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.code = 200;
        response.message = message;
        response.data = data;
        return response;
    }
    
    public static CommonResponse<?> createForError(String message) {
        CommonResponse<?> response = new CommonResponse<>();
        response.code = 500;
        response.message = message;
        return response;
    }
}
```

---

##  测试建议

### 单元测试

- 度量计算逻辑验证（DIT、NOC、CBO、WMC、RFC、LCOM）
- 代码行统计准确性（空行、注释、代码）
- UML 解析正确性（类、方法、参数提取）
-  **AI 提示词生成** 是否包含必要信息
-  **AI 结果解析** 是否正确处理各种返回格式

### 集成测试

- API 端点调用（通过 Postman 或 curl）
-  **AI 服务集成** - 测试 DeepSeek API 调用、容错处理
-  **端到端流程** - 上传文件 → 度量 → AI 分析 → 优化建议
- AI 服务稳定性（返回格式、容错处理）
- 端到端流程（上传文件 → 解析 → 度量 → 返回）

### 性能测试

- 大文件处理能力（代码行数、UML 复杂度）
- 并发请求处理能力
- AI 调用延迟

---

##  与前端集成

前端项目地址：[CodeMatrix-Vue3](https://github.com/jasminelee162/CodeMatrix-Vue3)

### 集成要点

1. **CORS 跨域配置**：后端已通过 `@CrossOrigin` 开启跨域支持
2. **API 基础路径**：前端配置后端服务地址（如 `http://localhost:8080`）
3. **文件上传**：使用 `multipart/form-data` 格式上传文件
4. **响应格式**：统一使用 `CommonResponse<T>` 数据结构

### 前端交互示例

```javascript
// AI 度量评审
const reviewResult = await fetch('http://localhost:8080/ai/metric-review', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    projectName: '示例项目',
    metricSystem: 'CK度量',
    metrics: { DIT: 3.0, CBO: 5.0, WMC: 12.0 }
  })
});

// 代码行度量（文件上传）
const formData = new FormData();
formData.append('javaFile', file);
const codeLineResult = await fetch('http://localhost:8080/CodeLines/codeLine', {
  method: 'POST',
  body: formData
});

// 🤖 AI 自由对话
const aiChatResult = await fetch('http://localhost:8080/ai/chat', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    prompt: '如何降低代码耦合度？'
  })
});

// 🤖 AI 代码优化
const aiOptimizeResult = await fetch('http://localhost:8080/ai/optimize', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    code: xmlCode  // UML/XMI 代码
  })
});
```

---


##  AI 功能特别说明

### AI 对话与分析的优势

- **即时反馈**：不到 5 秒获得代码质量评估
- **自然语言理解**：支持自由提问，不限于预设场景
- **可执行建议**：AI 生成的优化方案可直接使用
- **学习价值**：理解代码优化原理和最佳实践

### AI 功能的应用场景

| 场景 | 使用接口 | 预期效果 |
|------|---------|---------|
| 快速了解代码质量 | `/ai/metric-review` | 获得结构化评估报告 |
| 学习代码优化知识 | `/ai/chat` | 获得 AI 解释和建议 |
| 自动生成重构方案 | `/ai/optimize` | 获得可用的优化后 XML |
| 咨询架构设计问题 | `/ai/chat` | 获得专业建议 |

---

##  许可证

本项目采用 MIT 许可证。详见 LICENSE 文件。

---

##  参考资源

### 度量相关

- CK 度量体系：Chidamber & Kemerer (1994) - A Metrics Suite for Object Oriented Design
- 功能点分析：IFPUG FPA 标准
- 用例点度量：Karner (1993) - Resource Estimation for Objectory Projects

### 技术栈

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis-Plus 文档](https://baomidou.com)
- [Swagger/OpenAPI 文档](https://swagger.io)
- [dom4j XML 解析](https://dom4j.github.io/)

---
