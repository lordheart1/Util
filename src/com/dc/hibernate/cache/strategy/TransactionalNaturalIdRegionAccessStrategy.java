package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.NaturalIdRegionImpl;
import org.hibernate.cache.CacheException;


public
class TransactionalNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {
    public TransactionalNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super(region);
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }
}
