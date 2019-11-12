package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.EntityRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;

public
class TransactionalEntityRegionAccessStrategy extends BaseEntityRegionAccessStrategy {
    public TransactionalEntityRegionAccessStrategy(EntityRegionImpl region) {
        super(region);
    }

    public boolean afterInsert(Object key, Object value, Object version) {
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) {
        return false;
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return this.insert(key, value, currentVersion);
    }
}
