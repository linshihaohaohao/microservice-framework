package cn.com.bluemoon.redis.repository;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class RedisRepository {
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	public Boolean set(final byte[] key, final byte[] value, final long liveTime) {
		return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return true;
			}
		});
	}

	public byte[] get(final byte[] key) {
		return (byte[]) redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key);
			}
		});
	}

	public Long del(final byte[]... keys) {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(keys);
			}
		});
	}

	public Set<byte[]> getKeys(final byte[] pattern) {
		return (Set<byte[]>) redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.keys(pattern);
			}
		});
	}

	public Boolean exists(final byte[] key) {
		return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key);
			}
		});
	}

	public Long dbSize() {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	public void flushDb() {
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return 1L;
			}
		});
	}
}
