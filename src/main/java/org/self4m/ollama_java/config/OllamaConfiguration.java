package org.self4m.ollama_java.config;

import org.springframework.ai.ollama.api.OllamaOptions;
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

    public OllamaOptions getChatOptions() {
        OllamaOptions options = OllamaOptions.builder()
                .model(OLLAMA_MODEL)
                .temperature(OLLAMA_TEMPERATURE)
                .numPredict(OLLAMA_MAX_TOKENS)
                .topP(OLLAMA_OPTION_TOP_P)
                .topK(OLLAMA_OPTION_TOP_K)
                .build();

        return options;
    }

}