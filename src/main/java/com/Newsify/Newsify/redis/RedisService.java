package com.Newsify.Newsify.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<Object> getAllNewsUrl() {
        return redisTemplate.opsForSet().members("all_news_urls");
    }

    public void addNewsUrl(String title) {
        redisTemplate.opsForSet().add("all_news_urls", title);
    }

    public void clearEverything(){
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return null;
        });
    }
}

