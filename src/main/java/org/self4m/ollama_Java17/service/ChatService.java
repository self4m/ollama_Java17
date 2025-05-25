package org.self4m.ollama_Java17.service;

import org.self4m.ollama_Java17.pojo.ChatParam;
import reactor.core.publisher.Flux;

public interface ChatService {
    String getResponse(ChatParam chatParam);

    Flux<String> getStreamResponse(ChatParam chatParam);
}
