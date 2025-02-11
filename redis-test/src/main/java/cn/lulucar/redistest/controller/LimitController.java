package cn.lulucar.redistest.controller;

import cn.lulucar.redistest.limiter.RedisLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenxiaolan
 * @ClassName LimitController
 * @date 2025/2/11 18:00
 * @description
 */
@RestController("/limit")
public class LimitController {
    private final RedisLimiter redisLimiter;

    public LimitController(RedisLimiter redisLimiter) {
        this.redisLimiter = redisLimiter;
    }

    @GetMapping("/")
    public String limit() {
        boolean allowed = redisLimiter.isAllowed();
        if (allowed) {
            return "允许访问！";
        } else {
            return "稍后再试！";
        }
    }
}
