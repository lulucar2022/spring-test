local lockKey = KEYS[1]   -- 锁的key
local lockValue = ARGV[1]   -- 锁的值
local lockExpireTime = tonumber(ARGV[2])  -- 锁的存活时间
local acquiredLock = redis.call('SET', lockKey, lockValue, 'NX', 'EX', lockExpireTime)  -- 获取锁
if acquiredLock then
    return true     -- 获取锁成功
else
    return false    -- 获取锁失败
end