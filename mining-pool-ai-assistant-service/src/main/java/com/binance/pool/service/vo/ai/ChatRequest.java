package com.binance.pool.service.vo.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    /**
     * 会话ID，用于多轮对话上下文管理
     * 如果不传，后端自动生成
     */
    private String conversationId;

    /**
     * 用户消息
     */
    private String message;
}
