package com.dc.hibernate.cache.impl;

import com.dc.hibernate.cache.Cache;
import com.dc.hibernate.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

    private Cache cache;

    public Cache getCache(String shortName) {
        return this.cache;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
