package com.binance.pool.config;

import com.binance.pool.service.cache.ComsumerRedis;
import com.binance.pool.service.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MessageListenerConfiguration {

    @Resource
    ComsumerRedis comsumerRedis;
    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);
        List<Topic> topicList = Arrays.asList(new PatternTopic(Constants.TOPIC_POOL_SAVINGS_EARN),
                new PatternTopic(Constants.TOPIC_POOL_SAVINGS_EARN_INTERESTS)
         );

        container.addMessageListener(comsumerRedis, topicList);
        return container;
    }

}
