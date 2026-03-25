package com.binance.pool.config;

import com.binance.pool.service.ai.tools.BatchBillingCheckTool;
import com.binance.pool.service.ai.tools.BillingCheckTool;
import com.binance.pool.service.ai.tools.CoinPriceTool;
import com.binance.pool.service.ai.tools.PoolFluctuationTool;
import com.binance.pool.service.ai.tools.PoolSavingsTool;
import com.binance.pool.service.ai.tools.UserBillingTool;
import com.binance.pool.service.ai.tools.UserHashrateFluctuationTool;
import com.binance.pool.service.ai.tools.UserHashrateTool;
import com.binance.pool.service.ai.tools.UserOverviewTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class AiChatConfig {

    @Value("classpath:prompts/system-prompt.st")
    private Resource systemPromptResource;

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ToolCallbackProvider toolCallbackProvider(
            BillingCheckTool billingCheckTool,
            BatchBillingCheckTool batchBillingCheckTool,
            UserHashrateTool userHashrateTool,
            UserHashrateFluctuationTool userHashrateFluctuationTool,
            PoolFluctuationTool poolFluctuationTool,
            UserOverviewTool userOverviewTool,
            UserBillingTool userBillingTool,
            CoinPriceTool coinPriceTool,
            PoolSavingsTool poolSavingsTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(
                        billingCheckTool,
                        batchBillingCheckTool,
                        userHashrateTool,
                        userHashrateFluctuationTool,
                        poolFluctuationTool,
                        userOverviewTool,
                        userBillingTool,
                        coinPriceTool,
                        poolSavingsTool
                )
                .build();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel, ChatMemory chatMemory, ToolCallbackProvider toolCallbackProvider) {
        // 读取模板文件，替换变量后作为 system prompt
        String systemPrompt = loadAndResolvePrompt();

        return ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .defaultTools(toolCallbackProvider)
                .build();
    }

    private String loadAndResolvePrompt() {
        try (Reader reader = new InputStreamReader(systemPromptResource.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
            String template = sb.toString();
            // 替换 StringTemplate 变量
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return template.replace("{current_time}", currentTime);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system prompt", e);
        }
    }
}
