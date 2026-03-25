package com.binance.pool.service.pool.impl;

import com.binance.pool.service.pool.AiChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    @Override
    public Flux<String> chatStream(String conversationId, String userMessage) {
        String convId = resolveConversationId(conversationId);
        log.info("Chat stream start, conversationId={}", convId);

        return chatClient.prompt()
                .user(userMessage)
                .advisors(advisor -> advisor.param("chat_memory_conversation_id", convId))
                .stream()
                .content()
                .doOnError(error -> log.error("Chat  stream error, conversationId={}", convId, error));
    }

    @Override
    public String chat(String conversationId, String userMessage) {
        String convId = resolveConversationId(conversationId);
        log.info("Chat sync start, conversationId={}", convId);

        return chatClient.prompt()
                .user(userMessage)
                .advisors(advisor -> advisor.param("chat_memory_conversation_id", convId))
                .call()
                .content();
    }

    @Override
    public void clearMemory(String conversationId) {
        if (conversationId != null) {
            chatMemory.clear(conversationId);
            log.info("Memory cleared for conversationId={}", conversationId);
        }
    }

    private String resolveConversationId(String conversationId) {
        if (conversationId == null || conversationId.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return conversationId;
    }
}
