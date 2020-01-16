package com.fh.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class  RedisPools {
    private static JedisPool jedisPool;

    private RedisPools(){}
    //创建连接池之前，设置连接池信息
    static {
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        jedisPool=new JedisPool("192.168.23.145",6379);
    }
    public static Jedis getJedis(){
        Jedis resource = jedisPool.getResource();
        return resource;
    }
    public static void returnJedis(Jedis jedis){
         if(jedis!=null){
              jedis.close();
         }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
