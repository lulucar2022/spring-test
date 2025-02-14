local lockKey = KEYS[1]
local lockValue = ARGV[1]
local expectedValue = redis.call('GET', lockKey)
if expectedValue == lockValue then
    redis.call('DEL', lockKey)
    return true
else
    return false
end