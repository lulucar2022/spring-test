package cn.lulucar.redistest.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName RedisInit
 * @date 2025/2/12 16:26
 * @description 
 */
@Configuration
@Slf4j
public class RedisInitConfig {

    private final Map<String, DefaultRedisScript<Boolean>> redisScriptMap;

    public RedisInitConfig(Map<String, DefaultRedisScript<Boolean>> redisScriptMap) {
        this.redisScriptMap = redisScriptMap;
    }

    @PostConstruct
    public void initLuaScripts() {
        log.info("Initializing Lua scripts. Current redisScriptMap size: {}", redisScriptMap.size());

        // 加载tryLock.lua脚本
        DefaultRedisScript<Boolean> tryLockScript = new DefaultRedisScript<>();
        tryLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("tryLock.lua")));
        tryLockScript.setResultType(Boolean.class);
        redisScriptMap.put("tryLockScript", tryLockScript);
        log.info("tryLockScript loaded successfully.");
        
        // 加载unlock.lua脚本
        DefaultRedisScript<Boolean> unlockScript = new DefaultRedisScript<>();
        unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("unlock.lua")));
        unlockScript.setResultType(Boolean.class);
        redisScriptMap.put("unlockScript", unlockScript);
        log.info("unlockScript loaded successfully.");

        log.info("Lua scripts initialized. Current redisScriptMap size: {}", redisScriptMap.size());
    }

    @Bean
    public Map<String, DefaultRedisScript<Boolean>> redisScriptMap() {
        return this.redisScriptMap;
    }
}