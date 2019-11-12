package com.dc.hibernate.cache.strategy;


import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.RegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public abstract class BaseRegionAccessStrategy implements RegionAccessStrategy {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, BaseRegionAccessStrategy.class.getName());

    BaseRegionAccessStrategy() {
    }

    protected abstract BaseGeneralDataRegion getInternalRegion();

    protected abstract boolean isDefaultMinimalPutOverride();

    public Object get(Object key, long txTimestamp) throws CacheException {
        return this.getInternalRegion().get(key);
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
        return this.putFromLoad(key, value, txTimestamp, version, this.isDefaultMinimalPutOverride());
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        if (key != null && value != null) {
            if (minimalPutOverride && this.getInternalRegion().contains(key)) {
                LOG.debugf("Item already cached: %s", key);
                return false;
            } else {
                LOG.debugf("Caching: %s", key);
                this.getInternalRegion().put(key, value);
                return true;
            }
        } else {
            return false;
        }
    }

    public SoftLock lockRegion() throws CacheException {
        return null;
    }

    public void unlockRegion(SoftLock lock) throws CacheException {
        this.evictAll();
    }

    public SoftLock lockItem(Object key, Object version) throws CacheException {
        return null;
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
    }

    public void remove(Object key) throws CacheException {
    }

    public void removeAll() throws CacheException {
        this.evictAll();
    }

    public void evict(Object key) throws CacheException {
        this.getInternalRegion().evict(key);
    }

    public void evictAll() throws CacheException {
        this.getInternalRegion().evictAll();
    }
}