package com.example.demo.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类
 * @author LynnLee
 */
@Component
public class RedisUtil {

	@Autowired
	private StringRedisTemplate redisTpl;
	
	public boolean set(String key, String value) {
		try {
			redisTpl.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean set(String key, String value, long time) {
		try {
			return set(key, value) && expire(key, time);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String get(String key) {
		return redisTpl.opsForValue().get(key);
	}
	
	/**
	 * 设置key生命周期（单位：秒）
	 * @param key
	 * @param time
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			return redisTpl.expire(key, time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取key剩余生命周期（单位：秒）
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		return redisTpl.getExpire(key, TimeUnit.SECONDS);
	}
	
	public long incr(String key, long delta) {
		return redisTpl.opsForValue().increment(key, delta);
	}
	
	public long decr(String key, long delta) {
		return redisTpl.opsForValue().increment(key, -delta);
	}
	
	public boolean lpush(String key, String value) {
		try {
			redisTpl.opsForList().leftPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String rpop(String key) {
		return redisTpl.opsForList().rightPop(key);
	}
	
}
