package com.redis.component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisService {

	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void expire(String key, long timeout) {
		if (timeout > 0) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	public void deleteKey(String key) {
		if (key != null) {
			redisTemplate.delete(key);
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteKey(String[] keys) {
		if (keys != null && keys.length > 0) {
			redisTemplate.delete(CollectionUtils.arrayToList(keys));
		}
	}

	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public long incr(String key, long incrStep) {
		if (incrStep < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, incrStep);
	}

	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	public Set<Object> sget(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	public long sset(String key, Object... value) {
		try {
			return redisTemplate.opsForSet().add(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public boolean sHasKey(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	public long sExpire(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public long sGetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public long sRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return null;
	}

	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return null;
	}

	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;
	}

	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return 0;
	}

	public void publishMessage(String channel, Object message) {
		redisTemplate.convertAndSend(channel, message);
	}

}
