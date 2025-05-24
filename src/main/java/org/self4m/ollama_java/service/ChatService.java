package org.self4m.ollama_java.service;

import org.self4m.ollama_java.pojo.ChatParam;
import reactor.core.publisher.Flux;

public interface ChatService {
    String getResponse(ChatParam chatParam);

    Flux<String> getStreamResponse(ChatParam chatParam);
}
