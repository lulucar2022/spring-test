package cn.lulucar.redistest.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

import java.util.Collections;

/**
 * @author wenxiaolan
 * @ClassName RedisLimiter
 * @date 2025/2/12 8:58
 * @description
 */
@Component
public class RedisLimiter {

    private final StringRedisTemplate redisTemplate;

    // 从配置文件中读取 ip 和 rate
    @Value("${redis.limiter.ip}")
    private String ip;

    @Value("${redis.limiter.rate}")
    private int rate;

    public RedisLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
        redisScript.setResultType(Boolean.class);

        // 执行脚本，传入key和参数
        Boolean result = redisTemplate.execute(redisScript, Collections.singletonList(ip), String.valueOf(rate));
        return result != null && result;
    }
}