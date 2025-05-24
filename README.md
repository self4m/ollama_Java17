# 说明
基于 Java 17 和 Spring Boot 3.4.5 开发  
使用`org.springframework.boot:spring-ai-starter-model-ollama`依赖  
调用`ollama`客户端 api 完成对话请求

# 配置修改
在`application.properties`文件中根据需求修改文档

# 接口文档
## 基本信息
- **URL**：`ollama/chat`、`ollama/stream_chat`
- **HTTP 方法**: POST
- **Content-Type**：`multipart/form-data`

## 请求参数

| 字段名  | 类型              | 是否必填 | 描述           |
| ---- | --------------- | ---- | ------------ |
| text | string          | 是    | 用户的问题文本      |
| file | MultipartFile[] | 否    | 上传的媒体文件，支持多个 |


## 示例请求表单内容
```
text: 请问你基于什么服务运行的？
file: example.pdf
file: example2.png
```

> 文件字段可以上传多个，字段名都为 `file`，后端会接收到 `MultipartFile[]` 数组。  

> 支持的文件类型参考如下：  
> 文档类支持：  
> PDF（.pdf）、CSV（.csv）、Word 旧版（.doc）、Word 新版（.docx）、Excel 旧版（.xls）、Excel 新版（.xlsx）、HTML 文件（.html）、纯文本（.txt）以及 Markdown（.md）格式。   
> 视频类支持：  
> Matroska 格式（.mkv）、QuickTime（.mov）、MP4（.mp4）、WebM（.webm）、Flash 视频（.flv）、MPEG 视频（.mpeg 或 .mpg）、Windows Media Video（.wmv）以及 3GP（.3gp）格式。   
> 图片类则支持：  
> PNG（.png）、JPEG（.jpg / .jpeg）、GIF（.gif）以及 WebP（.webp）格式。

---




