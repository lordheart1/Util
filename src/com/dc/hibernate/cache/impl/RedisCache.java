package com.dc.hibernate.cache.impl;

import com.dc.hibernate.cache.Cache;
import org.apache.log4j.Logger;
import org.hibernate.cache.spi.CacheKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisCache implements Cache {

    private String h = "JPA_CACHE";

    private RedisTemplate<Object, Object> redisObjectTemplate;

    private static Logger logger = Logger.getLogger(RedisCache.class);

    @Override
    public void put(Object key, Object value) {

        Serializable keyString = key.toString();
        
        if(logger.isDebugEnabled()) {

            StringBuilder sb = new StringBuilder("put ");
            sb.append("keyString = ").append(keyString);
            
            logger.debug(sb.toString());
        }

        this.redisObjectTemplate.opsForHash().put(h,keyString,value);
    }

    @Override
    public void evict(Object key) {

        Serializable keyString = key.toString();
        
        if(logger.isDebugEnabled()) {

            StringBuilder sb = new StringBuilder("evict ");
            sb.append("keyString = ").append(keyString);
            
            logger.debug(sb.toString());
        }


        this.redisObjectTemplate.opsForHash().delete(h,keyString);
    }

    @Override
    public void clear() {

        if(logger.isDebugEnabled()) {

            logger.debug("clear");
        }
      //  this.redisObjectTemplate.opsForHash().delete();
    }

    @Override
    public Object get(Object key) {

        Serializable keyString = key.toString();
        
        if(logger.isDebugEnabled()) {
        	
        	logger.debug("get key=" + key);
        }

        return this.redisObjectTemplate.opsForHash().get(h,keyString);

    }

    @Override
    public boolean exists(Object key) {

        Serializable keyString = key.toString();

        boolean result = this.redisObjectTemplate.opsForHash().hasKey(h,keyString);

        return result;
    }

    @Override
    public List<Object> getStatistics() {

        return (List<Object>)this.redisObjectTemplate.opsForHash().entries(h).values();
    }

    @Override
    public Map toMap() {

        if(logger.isDebugEnabled()) {

            logger.debug("toMap");
        }
        return this.redisObjectTemplate.opsForHash().entries(h);
    }

    public RedisTemplate<Object, Object> getRedisObjectTemplate() {
        return redisObjectTemplate;
    }

    public void setRedisObjectTemplate(RedisTemplate<Object, Object> redisObjectTemplate) {
        this.redisObjectTemplate = redisObjectTemplate;
    }
}