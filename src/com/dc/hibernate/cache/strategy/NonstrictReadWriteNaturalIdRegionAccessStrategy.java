package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.NaturalIdRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;


public class NonstrictReadWriteNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {
    public NonstrictReadWriteNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super(region);
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.evict(key);
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }
}