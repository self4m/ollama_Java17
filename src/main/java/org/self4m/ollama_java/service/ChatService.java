package org.self4m.ollama_java.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    String getResponse(String text);

    Flux<String> getStreamResponse(String text);
}
