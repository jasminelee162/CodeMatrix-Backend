## 电子商务

## AI 对话功能

本项目已集成一个简单的 AI 对话接口，使用阿里云百炼（OpenAI 兼容）进行问答。

### 接口说明

- 请求地址：`POST http://<host>:8080/api/ai/chat`
- 请求 Content-Type：`application/json`
- 请求体模板：
	```json
	{
		"prompt": "你好，帮我介绍一下这个项目"
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
ai.chat.api.url=https://api.openai.com/v1/chat/completions
ai.chat.api.key=sk-1ef80b56460543df9ba3e2a285d48f1b
```

已将 API Key 直接写入配置，方便小组成员运行，但仅建议在课堂作业环境中使用。