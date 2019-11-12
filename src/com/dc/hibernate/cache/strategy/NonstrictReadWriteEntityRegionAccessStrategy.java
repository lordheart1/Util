package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.EntityRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;


public
class NonstrictReadWriteEntityRegionAccessStrategy extends BaseEntityRegionAccessStrategy {
    public NonstrictReadWriteEntityRegionAccessStrategy(EntityRegionImpl region) {
        super(region);
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.evict(key);
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        this.evict(key);
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        this.unlockItem(key, lock);
        return false;
    }

    public void remove(Object key) throws CacheException {
        this.evict(key);
    }
}