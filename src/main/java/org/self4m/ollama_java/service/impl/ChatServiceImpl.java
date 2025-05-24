package org.self4m.ollama_java.service.impl;

import jakarta.annotation.Resource;
import org.self4m.ollama_java.config.OllamaConfiguration;
import org.self4m.ollama_java.pojo.ChatParam;
import org.self4m.ollama_java.service.ChatService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Resource
    private OllamaConfiguration ollamaConfiguration;

    // 系统提示词
    SystemMessage systemMessage = SystemMessage.builder()
            .text("""
你是一个基于 Ollama 一个开源的大语言部署服务工具部署AI 模型，职责是帮助用户解答各类问题，并提供清晰、实用的解释与技术建议。
当用户询问你是谁、由什么提供服务、你背后的平台时，你应明确回答：“我是一个使用 Ollama 部署并提供服务的 AI 模型”。
请以自然、亲切的语言回答问题，避免生硬或教条式的用语。
""")
            .build();


    @Override
    public String getResponse(ChatParam chatParam) {
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        UserMessage userMessage = null;
        userMessage = getUserMessage(chatParam, userMessage);
        if (userMessage == null) return "文件读取失败";
        messages.add(userMessage);
        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        ChatResponse chatResponse=ollamaChatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public Flux<String> getStreamResponse(ChatParam chatParam) {
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        UserMessage userMessage = null;
        userMessage = getUserMessage(chatParam, userMessage);
        if (userMessage == null) return Flux.just("文件读取失败");
        messages.add(userMessage);
        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        Flux<ChatResponse> chatResponseFlux = ollamaChatModel.stream(prompt);
        return chatResponseFlux.mapNotNull(chatResponse -> chatResponse.getResults().get(0).getOutput().getText());
    }

    private static UserMessage getUserMessage(ChatParam chatParam, UserMessage userMessage) {
        // 检查是否存在文件
        if (chatParam.getFile() != null && chatParam.getFile().length > 0) {
            List<Media> medias  = new ArrayList<>();
            for (MultipartFile file : chatParam.getFile()) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = null;
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    return null;
                }
                if (fileName.endsWith(".pdf")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_PDF).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".csv")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_CSV).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".doc")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_DOC).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".docx")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_DOCX).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".xls")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_XLS).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".xlsx")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_XLSX).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_HTML).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".txt")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_TXT).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".md")) {
                    medias.add(Media.builder().mimeType(Media.Format.DOC_MD).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".mkv")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_MKV).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".mov")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_MOV).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".mp4")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_MP4).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".webm")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_WEBM).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".flv")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_FLV).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".mpeg") || fileName.endsWith(".mpg")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_MPEG).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".wmv")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_WMV).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".3gp")) {
                    medias.add(Media.builder().mimeType(Media.Format.VIDEO_THREE_GP).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".png")) {
                    medias.add(Media.builder().mimeType(Media.Format.IMAGE_PNG).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    medias.add(Media.builder().mimeType(Media.Format.IMAGE_JPEG).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".gif")) {
                    medias.add(Media.builder().mimeType(Media.Format.IMAGE_GIF).data(bytes).name(fileName).build());
                } else if (fileName.endsWith(".webp")) {
                    medias.add(Media.builder().mimeType(Media.Format.IMAGE_WEBP).data(bytes).name(fileName).build());
                }
                // 用户消息
                userMessage = UserMessage.builder().text(chatParam.getText()).media(medias).build();
            }
        }else{
            // 用户消息
            userMessage = UserMessage.builder().text(chatParam.getText()).build();
        }
        return userMessage;
    }
}
