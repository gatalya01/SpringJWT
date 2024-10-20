package com.example.spring_jwt.event;

import com.example.spring_jwt.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handlerRedisKeyExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event){
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();

        if(expiredRefreshToken == null){
            throw new RuntimeException("Refresh token is null in handlerRedisKeyExpiredEvent function!");
        }
        log.info("Refresh token with key={} has expired: Refresh token is: {}" , expiredRefreshToken.getId() ,
                expiredRefreshToken.getToken());
    }
}
