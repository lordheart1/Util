package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.CollectionRegionImpl;
import org.hibernate.cache.CacheException;


public class TransactionalCollectionRegionAccessStrategy extends BaseCollectionRegionAccessStrategy {
    public TransactionalCollectionRegionAccessStrategy(CollectionRegionImpl region) {
        super(region);
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }
}

