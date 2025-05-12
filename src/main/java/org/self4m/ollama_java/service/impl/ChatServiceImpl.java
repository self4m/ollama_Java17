package org.self4m.ollama_java.service.impl;

import jakarta.annotation.Resource;
import org.self4m.ollama_java.config.OllamaConfiguration;
import org.self4m.ollama_java.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Autowired
    private OllamaConfiguration ollamaConfiguration;

    @Override
    public String getResponse(String text) {
        ChatResponse chatResponse=ollamaChatModel.call(new Prompt(text, ollamaConfiguration.getChatOptions()));
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public Flux<String> getStreamResponse(String text) {
        Flux<ChatResponse> chatResponseFlux = ollamaChatModel.stream(
                new Prompt(text, ollamaConfiguration.getChatOptions())
        );

        return chatResponseFlux.map(chatResponse -> {
            String data = chatResponse.getResults().get(0).getOutput().getText();
            return data;
        });
    }
}
