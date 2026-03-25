package com.binance.pool.service.cache;

import com.binance.pool.service.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;


@Slf4j
@Service
public class ComsumerRedis implements MessageListener {


    @Resource
    UserSavingCacheServer userSavingCacheServer;

    @Resource
    UserCacheServer userCacheServer;


    @Override
    public void onMessage(Message message, byte[] bytes) {
        StringBuilder strLog = new StringBuilder();
        strLog.append(" onMessage ");
        try {
            String topic = new String(message.getChannel());
            String content = new String(message.getBody());
            strLog.append(" ComsumerRedis  topic:" + topic);
            strLog.append(" content:" + content);
            log.info(strLog.toString());
            if (topic.equals(Constants.TOPIC_POOL_SAVINGS_EARN)) {
                userSavingCacheServer.invalidSavingsAssetsCache();
            }
            if (topic.equals(Constants.TOPIC_POOL_SAVINGS_EARN_INTERESTS)) {
                userSavingCacheServer.invalidSavingsInterestsCache();
            }


        } catch (Exception ex) {
            log.error(strLog.toString() + " 消息通知异常", ex);
        }


    }
}
