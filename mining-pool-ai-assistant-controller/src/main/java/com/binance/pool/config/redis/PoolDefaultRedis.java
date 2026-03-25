package com.binance.pool.config.redis;

import com.binance.pool.config.bean.AwsRedisConfig;
import com.binance.pool.config.bean.SecurityNameConfig;
import com.binance.pool.dao.util.RedisUtil;
import com.binance.pool.util.SecurityManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by yyh on 2020/4/28
 */
@Slf4j
@Configuration
public class PoolDefaultRedis {


    @Autowired
    SecurityNameConfig securityNameConfig;

    @Bean("poolRedisConfig")
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisStandaloneConfiguration redisConfig() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        AwsRedisConfig redisConfig = SecurityManagerUtil.getRedisConfig(securityNameConfig.getRedis().get(0),securityNameConfig.getRedis().get(1));
        configuration.setHostName(redisConfig.getHost());
        configuration.setPort(redisConfig.getPort());
        if(StringUtils.isEmpty(redisConfig.getRedisPass())){
            configuration.setPassword("");
        }else{
            configuration.setPassword(redisConfig.getRedisPass());
        }
        return configuration;
    }
    @Bean("redisFactory")
    @Primary
    public LettuceConnectionFactory factory(GenericObjectPoolConfig config, @Qualifier("poolRedisConfig") RedisStandaloneConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }
    /**
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, String> functionDomainRedisTemplate2(@Qualifier("redisFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        log.info("get redisTemplate ok. ");
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, String> redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 注入封装RedisTemplate
     *
     * @return RedisUtil
     * @throws
     * @Title: redisUtil
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
    /**
     * redislistener
     */
    @Bean(name = "redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();

        listenerContainer.setConnectionFactory(redisConnectionFactory);
        return listenerContainer;
    }
}
