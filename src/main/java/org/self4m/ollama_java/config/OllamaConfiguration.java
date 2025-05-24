package org.self4m.ollama_java.config;

import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfiguration {

    @Value("${spring.ai.ollama.model}")
    private String OLLAMA_MODEL;

    // 可根据官方文档进行配置
    public OllamaOptions getChatOptions() {
        return OllamaOptions.builder()
                .model(OLLAMA_MODEL)
                .build();
    }

}