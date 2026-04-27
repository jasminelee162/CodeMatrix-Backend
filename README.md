## AI 对话功能

项目已集成基础 AI 对话接口（DeepSeek）。

### 接口说明

- 请求地址：`POST /api/ai/chat`
- 请求头：`Content-Type: application/json`
- 请求体示例：

```json
{
  "prompt": "你好，你是什么模型？"
}
```

### AI 指标评价与建议

该接口对你传入的指标结果进行评价与改进建议输出。

- 请求地址：`POST /api/ai/metric-review`
- 请求头：`Content-Type: application/json`
- 请求体示例：

```json
{
  "projectName": "CodeMatrix",
  "metricSystem": "CK",
  "context": "核心模块近期需求迭代频繁",
  "metrics": {
    "WMC": 38,
    "CBO": 14,
    "RFC": 92,
    "LCOM": 0.72
  }
}
```

你可以按不同度量体系传入不同指标，下面给出 3 组可直接使用的示例。

#### 示例1：功能点 IFPUG（UFP、VAF、FP）

```json
{
  "projectName": "CodeMatrix",
  "metricSystem": "IFPUG",
  "context": "功能点估算与复核",
  "metrics": {
    "UFP": 132,
    "VAF": 1.03,
    "FP": 135.96
  }
}
```

#### 示例2：用例点度量（UUCP、TCF、EF、UPC）

```json
{
  "projectName": "CodeMatrix",
  "metricSystem": "UseCasePoint",
  "context": "需求阶段用例点估算",
  "metrics": {
    "UUCP": 96,
    "TCF": 0.95,
    "EF": 1.05,
    "UPC": 95.76
  }
}
```

#### 示例3：代码度量（注释行数、非注释行数、物理代码行数、逻辑代码行数）

```json
{
  "projectName": "CodeMatrix",
  "metricSystem": "Code",
  "context": "版本发布前代码规模评估",
  "metrics": {
    "注释行数": 320,
    "非注释行数": 4280,
    "物理代码行数": 4600,
    "逻辑代码行数": 3890
  }
}
```

- 返回体核心字段：
  - `overallAssessment`：总体评价
  - `riskLevel`：风险等级
  - `keyFindings`：关键发现
  - `suggestions`：改进建议

### 配置说明

AI 配置位于 `src/main/resources/application.properties`：

```properties
ai.chat.api.url=https://api.deepseek.com/v1/chat/completions
ai.chat.api.key=sk-8dac650632804c9a8e9213a80144baf0
```
