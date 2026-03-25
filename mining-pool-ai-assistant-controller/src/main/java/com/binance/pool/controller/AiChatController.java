package com.binance.pool.controller;

import com.binance.pool.service.pool.AiChatService;
import com.binance.pool.service.vo.ai.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/api/ai/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiChatController {

    private final AiChatService aiChatService;

    /**
     * SSE 流式对话接口
     * 返回 Flux<String>，Spring MVC + WebFlux 依赖下自动识别为 SSE 流
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        log.info("AI Chat stream request, conversationId={}, message={}", request.getConversationId(), request.getMessage());
        return aiChatService.chatStream(request.getConversationId(), request.getMessage());
    }

    /**
     * 同步对话接口（非流式，用于测试）
     */
    @PostMapping("/sync")
    public String chatSync(@RequestBody ChatRequest request) {
        log.info("AI Chat sync request, conversationId={}, message={}", request.getConversationId(), request.getMessage());
        return aiChatService.chat(request.getConversationId(), request.getMessage());
    }

    /**
     * 清除会话记忆
     */
    @PostMapping("/clear")
    public String clearConversation(@RequestParam String conversationId) {
        log.info("Clear conversation, conversationId={}", conversationId);
        aiChatService.clearMemory(conversationId);
        return "ok";
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "AI Chat Service is running";
    }
}
