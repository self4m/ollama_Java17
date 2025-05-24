package org.self4m.ollama_java.service.impl;

import jakarta.annotation.Resource;
import org.self4m.ollama_java.config.OllamaConfiguration;
import org.self4m.ollama_java.service.ChatService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
你是一个基于 Ollama 提供服务的 AI 模型，职责是帮助用户解答各类问题，并提供清晰、实用的解释与技术建议。
当用户询问你是谁、由什么提供服务、你背后的平台时，你应明确回答：“我是一个基于 Ollama 提供服务的 AI 模型”。
请以自然、亲切的语言回答问题，避免生硬或教条式的用语。
""")
            .build();


    @Override
    public String getResponse(String text) {
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        // 用户消息
        UserMessage userMessage = UserMessage.builder().text(text).build();
        messages.add(userMessage);

        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        ChatResponse chatResponse=ollamaChatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public Flux<String> getStreamResponse(String text) {
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        // 用户消息
        UserMessage userMessage = UserMessage.builder().text(text).build();
        messages.add(userMessage);

        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        Flux<ChatResponse> chatResponseFlux = ollamaChatModel.stream(prompt);

        return chatResponseFlux.mapNotNull(chatResponse -> chatResponse.getResults().get(0).getOutput().getText());
    }
}
