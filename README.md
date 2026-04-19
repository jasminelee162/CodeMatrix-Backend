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