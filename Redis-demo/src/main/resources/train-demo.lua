local count = tonumber(redis.call('get',KEYS[1]))
local quantity = tonumber(ARGV[1])
if (count >= quantity) then
    return redis.call('decrby',KEYS[1],quantity)
else
    return -1
end