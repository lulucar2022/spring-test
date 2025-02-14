local ipKey = KEYS[1];  -- IP 地址
local rate = tonumber(ARGV[1]); -- 请求次数
local requestCount = redis.call('incr', ipKey); -- 每次请求+1
if requestCount == 1 then   -- 第一次请求
    redis.call('expire', ipKey, 60);    -- 设置过期时间（60S）
    return true
elseif requestCount > rate then     -- 当前请求次数大于允许的请求次数
    return false;
end
