package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.CollectionRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;


public
class NonstrictReadWriteCollectionRegionAccessStrategy extends BaseCollectionRegionAccessStrategy {
    public NonstrictReadWriteCollectionRegionAccessStrategy(CollectionRegionImpl region) {
        super(region);
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.evict(key);
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }
}
