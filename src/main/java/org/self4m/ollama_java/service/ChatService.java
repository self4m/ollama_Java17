package org.self4m.ollama_java.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ChatService {
    String getResponse(String text);

    Flux<String> getStreamResponse(String text);
}
