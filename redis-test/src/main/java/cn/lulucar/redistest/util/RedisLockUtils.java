package cn.lulucar.redistest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName RedisLockUtils
 * @date 2025/2/12 14:51
 * @description
 */
@Component
public class RedisLockUtils {
    
    private static final Logger log = LoggerFactory.getLogger(RedisLockUtils.class);
    private final Map<String, DefaultRedisScript<Boolean>> redisScriptMap;
    private final StringRedisTemplate stringRedisTemplate;
    
    public RedisLockUtils(Map<String, DefaultRedisScript<Boolean>> redisScriptMap, StringRedisTemplate stringRedisTemplate) {
        this.redisScriptMap = redisScriptMap;
        this.stringRedisTemplate = stringRedisTemplate;
        if (redisScriptMap.size()<2) {
            log.error("map not exists");
        }
    }

    public  boolean tryLock(String lockKey, String value, String expireTime) {
        DefaultRedisScript<Boolean> tryLockScript = redisScriptMap.get("tryLockScript");
        if (tryLockScript == null) {
            log.error("{}, not found!", tryLockScript);
            return false;
        }
        Boolean result = stringRedisTemplate.execute(tryLockScript, Collections.singletonList(lockKey), value, expireTime);
        return result;
    }
    
    public  boolean unlock(String lockKey, String value) {
        DefaultRedisScript<Boolean> unlockScript = redisScriptMap.get("unlockScript");
        if (unlockScript == null) {
            log.error("{}, not found", unlockScript);
            return false;
        }
        Boolean result = stringRedisTemplate.execute(unlockScript, Collections.singletonList(lockKey), value);
        return result;

    }
}
