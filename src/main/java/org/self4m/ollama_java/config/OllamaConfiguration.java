package org.self4m.ollama_java.config;

import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfiguration {

    @Value("${spring.ai.ollama.model}")
    private String OLLAMA_MODEL;

    @Value("${spring.ai.ollama.chat.options.temperature}")
    private double OLLAMA_TEMPERATURE;

    @Value("${spring.ai.ollama.chat.options.max-tokens}")
    private int OLLAMA_MAX_TOKENS;

    @Value("${spring.ai.ollama.chat.options.top-p}")
    private double OLLAMA_OPTION_TOP_P;

    @Value("${spring.ai.ollama.chat.options.top-k}")
    private int OLLAMA_OPTION_TOP_K;

    public ChatOptions getChatOptions() {
        ChatOptions chatOptions = ChatOptions.builder()
                // 模型名称
                .model(OLLAMA_MODEL)
                // 模型温度
                .temperature(OLLAMA_TEMPERATURE)
                // 最大输入输出token
                .maxTokens(OLLAMA_MAX_TOKENS)
                // 累积概率采样
                .topP(OLLAMA_OPTION_TOP_P)
                // 概率抽取量
                .topK(OLLAMA_OPTION_TOP_K)
                .build();
        return chatOptions;
    }

}