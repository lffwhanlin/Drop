package jafka.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	private static JedisPool pool;
	
	static {
		JedisPoolConfig config = new JedisPoolConfig();
	    config.setMaxIdle(10);
	    config.setMaxTotal(10);
	    config.setMaxWaitMillis(1000);
	    config.setTestOnBorrow(true);
	    config.setTestWhileIdle(true);
	    config.setTimeBetweenEvictionRunsMillis(10 * 1000);
	    pool = new JedisPool(config,"127.0.0.1",6379,10000);
	}
	
	public static String get(String key){
		Jedis jedis = pool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}
	
	public static void set(String key ,String value){
		Jedis jedis = pool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
}
