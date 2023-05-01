package com.Newsify.Newsify.redis;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/url")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping
    public Set<Object> getAllNewsUrl() {
        return redisService.getAllNewsUrl();
    }

    @DeleteMapping
    public void deleteEverything() {
        redisService.clearEverything();
    }
}
