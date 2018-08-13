package cn.com.bluemoon.redis.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bluemoon.redis.repository.RedisRepository;
import cn.com.bluemoon.redis.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {
	
	private static String redisCode = "utf-8";
	
	@Autowired
	private RedisRepository repository;
	
	@Override
	public boolean set(String key, String value) {
		return repository.set(key.getBytes(), value.getBytes(), 0);
	}
	
	@Override
	public boolean set(String key, String value, long liveTime) {
		return repository.set(key.getBytes(), value.getBytes(), liveTime);
	}
	
	@Override
	public boolean set(String key, Object value) {
		return repository.set(key.getBytes(), toByteArray(value), 0);
	}
	
	@Override
	public boolean set(String key, Object value, long liveTime) {
		return repository.set(key.getBytes(), toByteArray(value), liveTime);
	}
	
	@Override
	public String get(String key) {
		byte[] value = repository.get(key.getBytes());
		if (value != null) {
			try {
				return new String(value, redisCode);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return null;
	}
	
	@Override
	public Object getObj(String key) {
		byte[] value = repository.get(key.getBytes());
		if (value != null) {
			return toObject(value);
		}
		return null;
	}
	
	@Override
	public Set<String> getKeys(String pattern) {
		Set<byte[]> keys = repository.getKeys(pattern.getBytes());
		if (keys != null) {
			Set<String> StrKeys = new HashSet<String>();
			for (byte[] key : keys) {
				try {
					StrKeys.add(new String(key, redisCode));
				} catch (UnsupportedEncodingException e) {
					continue;
				}
			}
			return StrKeys;
		}
		return null;
	}
	
	@Override
	public Long del(String key) {
		return repository.del(key.getBytes());
	}
	
	@Override
	public Boolean exists(String key) {
		return repository.exists(key.getBytes());
	}
	
	/**
	 * 描述 : <Object转byte[]>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}
	
	/**
	 * 描述 : <byte[]转Object>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 */
	private Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
