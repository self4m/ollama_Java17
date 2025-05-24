package org.self4m.ollama_java.controller;

import lombok.extern.slf4j.Slf4j;
import org.self4m.ollama_java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("ollama")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping(value = "chat")
    // 一次性对话
    public String disposableChat(@RequestParam String text){
        log.debug("接收到的内容是:{}", text);
        String message = chatService.getResponse(text);
        log.debug("返回的结果是:{}",message.replaceAll("[\\r\\n]+", " "));
        return message;
    }

    @GetMapping(value = "stream_chat/",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // 按照 SSE 标准进行返回
    // 一次性流式对话
    public ResponseEntity<Flux<String>> disposableStreamChat(@RequestParam String text){
        log.debug("接收到的内容是:{}", text);
        Flux<String> message = chatService.getStreamResponse(text);
        return ResponseEntity.ok()
                .body(message);
    }

}