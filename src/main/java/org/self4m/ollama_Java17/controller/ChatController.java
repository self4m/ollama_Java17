package org.self4m.ollama_Java17.controller;

import lombok.extern.slf4j.Slf4j;
import org.self4m.ollama_Java17.pojo.ChatParam;
import org.self4m.ollama_Java17.service.ChatService;
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

    @PostMapping(value = "chat", consumes = "multipart/form-data")
    // 一次性对话
    public String disposableChat(@ModelAttribute ChatParam chatParam){
        log.info("接收到的提问的内容是:{}", chatParam.getText());
        String message = chatService.getResponse(chatParam);
        log.info("返回的结果是:{}",message.replaceAll("[\\r\\n]+", " "));
        return message;
    }

    @PostMapping(value = "stream_chat", consumes = "multipart/form-data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // 按照 SSE 标准进行返回
    // 一次性流式对话
    public ResponseEntity<Flux<String>> disposableStreamChat(@ModelAttribute ChatParam chatParam){
        log.info("接收到的提问的内容是:{}", chatParam.getText());
        Flux<String> message = chatService.getStreamResponse(chatParam);
        return ResponseEntity.ok()
                .body(message);
    }

}