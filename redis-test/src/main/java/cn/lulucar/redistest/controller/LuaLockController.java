package cn.lulucar.redistest.controller;

import cn.lulucar.redistest.util.RedisLockUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wenxiaolan
 * @ClassName LuaLockController
 * @date 2025/2/12 18:05
 * @description
 */
@RestController
@RequestMapping("/lock")
@Data
@Slf4j
public class LuaLockController {
    
    private static Integer stockCount = 100;
    private static final String UUID = String.valueOf(java.util.UUID.randomUUID());
    private final RedisLockUtils redisLockUtils;

    public LuaLockController(RedisLockUtils redisLockUtils) {
        this.redisLockUtils = redisLockUtils;
    }

    @GetMapping("/")
    public String luaLock() {
        try {
            // 获取锁
            if (redisLockUtils.tryLock(UUID, UUID, "10")) {
                // 减库存
                if (stockCount > 0) {
                    stockCount -= 1;
                    log.info("减库成功，剩余库存：{}", stockCount);
                } else {
                    log.warn("库存不足！");
                }
            } else {
                log.warn("获取锁失败！");
            }
        } catch (Exception e) {
            log.error("发生异常", e);
        } finally {
            redisLockUtils.unlock(UUID,UUID);
        }
        return "操作完成";
    }
}
