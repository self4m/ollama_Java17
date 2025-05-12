package org.self4m.ollama_java.controller;

import lombok.extern.slf4j.Slf4j;
import org.self4m.ollama_java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("ollama")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping(value = "chat/{text}")
    // 一次性对话
    public String disposableChat(@PathVariable String text){
        log.debug("接收到的文本的是:{}", text);
        String message = chatService.getResponse(text);
        log.debug("返回的结果是:{}",message.replaceAll("[\\r\\n]+", " "));
        return message;
    }

    // 按照 SSE 标准进行返回
    @GetMapping(value = "stream_chat/{text}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // 一次性流式对话
    public ResponseEntity<Flux<String>> disposableStreamChat(@PathVariable String text){
        log.debug("接收到的文本的是:{}", text);
        Flux<String> message = chatService.getStreamResponse(text);
        return ResponseEntity.ok()
                .body(message);
    }

    // 仅按照便于查看的格式进行流式返回
    @GetMapping(value = "stream_chat_view/{text}")
    // 一次性流式对话
    public ResponseEntity<Flux<String>> disposableStreamChat_view(@PathVariable String text){
        log.debug("接收到的文本的是:{}", text);
        Flux<String> message = chatService.getStreamResponse(text);
        return ResponseEntity.ok()
                .body(message);
    }


}