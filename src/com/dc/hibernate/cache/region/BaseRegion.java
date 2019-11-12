package com.dc.hibernate.cache.region;

import com.dc.hibernate.cache.Cache;
import com.dc.hibernate.cache.CacheManager;
import com.dc.hibernate.cache.CacheManagerFactory;
import com.dc.hibernate.cache.impl.RedisCache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;
//import org.hibernate.testing.cache.Timestamper;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseRegion implements Region {
    protected   CacheManager cacheManager = CacheManagerFactory.getCacheManager();
    private final String name;
    private static int timeout = 1024;

    BaseRegion(String name) {
        this.name = name;
    }

    public boolean contains(Object key) {
        return key != null ? this.getCache().exists(key) : false;
    }

    public String getName() {
        return this.name;
    }

    public void destroy() throws CacheException {
        this.getCache().clear();
    }

    public long getSizeInMemory() {
        return -1L;
    }

    public long getElementCountInMemory() {
        return (long)this.getCache().getStatistics().size();
    }

    public long getElementCountOnDisk() {
        return 0L;
    }

    public Map toMap() {
        return Collections.unmodifiableMap(this.getCache().toMap());
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public int getTimeout() {
        return timeout;
    }

    public Cache getCache() {

        if(this.cacheManager == null) {
            this.cacheManager = CacheManagerFactory.getCacheManager();
        }

        return this.cacheManager.getCache("");
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
