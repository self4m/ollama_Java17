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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Autowired
    private OllamaConfiguration ollamaConfiguration;

    // 系统提示词
    String systemMessage = "你是一个 ai 助手,可以帮助我解决问题";

    @Override
    public String getResponse(String text) {

        List<Message> messages = List.of(new SystemMessage(systemMessage), new UserMessage(text));
        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        ChatResponse chatResponse=ollamaChatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public Flux<String> getStreamResponse(String text) {
        List<Message> messages = List.of(new SystemMessage(systemMessage), new UserMessage(text));
        Prompt prompt = new Prompt(messages, ollamaConfiguration.getChatOptions());
        Flux<ChatResponse> chatResponseFlux = ollamaChatModel.stream(prompt);

        return chatResponseFlux.map(chatResponse -> {
            String data = chatResponse.getResults().get(0).getOutput().getText();
            return data;
        });
    }
}
