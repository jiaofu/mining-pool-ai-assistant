package com.binance.pool.service.pool;

import reactor.core.publisher.Flux;

public interface AiChatService {

    /**
     * 流式对话（Flux SSE）
     */
    Flux<String> chatStream(String conversationId, String userMessage);

    /**
     * 同步对话
     */
    String chat(String conversationId, String userMessage);

    /**
     * 清除会话记忆
     */
    void clearMemory(String conversationId);
}
