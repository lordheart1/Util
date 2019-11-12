package com.dc.hibernate.cache.impl;

import com.dc.hibernate.cache.Cache;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalMapCache implements Cache {

    private static Logger logger = Logger.getLogger(RedisCache.class);

    private Map<Object,Object> redis = new HashMap<Object,Object>();

    @Override
    public void put(Object key, Object value) {

        if(logger.isDebugEnabled()) {

            logger.debug("put:");
            logger.debug(key);
            logger.debug(value);
        }

        this.redis.put(key,value);
    }

    @Override
    public void evict(Object key) {

        if(logger.isDebugEnabled()) {

            logger.debug("evict");
            logger.debug(key);
        }

        this.redis.remove(key);
    }

    @Override
    public void clear() {

        if(logger.isDebugEnabled()) {

            logger.debug("clear");
        }
        this.redis.clear();
    }

    @Override
    public Object get(Object key) {

        Object value = this.redis.get(key);

        if(logger.isDebugEnabled()) {
            logger.debug("get");
            logger.debug(key);
            logger.debug(value);
        }


        return value;
    }

    @Override
    public boolean exists(Object key) {

        boolean result = this.redis.containsKey(key);
        if(logger.isDebugEnabled()) {

            logger.debug("exists");
            logger.debug(key);
            logger.debug(result);
        }
        return result;
    }

    @Override
    public List<Object> getStatistics() {

        return (List<Object>)this.redis.values();
    }

    @Override
    public Map toMap() {

        if(logger.isDebugEnabled()) {

            logger.debug("toMap");
        }
        return this.redis;
    }
}
