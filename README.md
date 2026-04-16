## 电子商务

## AI 对话功能

本项目已集成一个简单的 AI 对话接口，使用阿里云百炼（OpenAI 兼容）进行问答。

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
ai.chat.api.url=https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation
ai.chat.api.key=sk-1ef80b56460543df9ba3e2a285d48f1b
```

**注意**：如果账户余额不足，接口会返回友好提示信息，不会崩溃。