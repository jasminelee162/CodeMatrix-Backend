## AI 对话功能

本项目已集成一个简单的 AI 对话接口，使用 DeepSeek 进行问答。

### 接口说明

- 请求地址：`POST http://<host>:8080/api/ai/chat`
- 请求 Content-Type：`application/json`
- 请求体模板：
	```json
	{
		"prompt": "你好，你是什么模型？"
	}
	```
- 返回数据结构：
	```json
	{
		"status": 0,
		"msg": "success",
		"data": {
			"answer": "..."
		}
	}
	```

### 配置说明

AI 配置在 `src/main/resources/application.properties` 中：

```properties
ai.chat.api.url=https://api.deepseek.com/v1/chat/completions
ai.chat.api.key=sk-8dac650632804c9a8e9213a80144baf0
```

**注意**：DeepSeek API 需要付费使用，请节约调用次数。接口会检查余额不足的情况并返回友好提示。