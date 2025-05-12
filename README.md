# 说明
通过 java 17 连接 ollama 进行对话  
为什么不用 java 8 ? 依赖不支持啦!!!  

# 接口使用
- 默认端口设置为`30000`  
- 日志保存在当前目录的`ollama.log`文件中,重启项目会清除之前的内容  
- 接口均使用`get`方法进行请求

## 接口 1
进行一次对话,等结果全部生成后才会返回  
`localhost:30000/ollama/chat/你的问题`

## 接口 2
进行一次对话,按照 SSE 标准进行流式返回  
`localhost:30000/ollama/stream_chat/你的问题`

## 接口 3
进行一次对话,仅按照便于查看的格式进行流式返回  
`localhost:30000/ollama/stream_chat_view/你的问题`