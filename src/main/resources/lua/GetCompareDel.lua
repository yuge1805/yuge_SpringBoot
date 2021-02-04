---.
--- DateTime: 2021/1/29 14:53
---
if redis.call('get', KEYS[1]) == ARGV[1] then
    return redis.call('del', KEYS[1])
else
    return 0
end