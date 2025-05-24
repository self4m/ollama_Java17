# 说明
基于 Java 17 和 Spring Boot 3.4.5 开发  
使用`org.springframework.boot:spring-ai-starter-model-ollama`依赖  
调用`ollama`客户端 api 完成对话请求  

# 接口使用
- 默认端口设置为`8080`   
- 根据行为接口均使用`get`方法进行请求

## 接口 1
进行一次对话,等结果全部生成后才会返回  
`localhost:30000/ollama/chat?text=你的问题`

## 接口 2
进行一次对话,按照 SSE 标准进行流式返回  
`localhost:30000/ollama/stream_chat?text=你的问题`