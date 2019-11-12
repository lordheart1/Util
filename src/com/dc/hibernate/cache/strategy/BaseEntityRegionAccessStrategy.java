package com.dc.hibernate.cache.strategy;


import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.EntityRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

class BaseEntityRegionAccessStrategy extends BaseRegionAccessStrategy implements EntityRegionAccessStrategy {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, BaseEntityRegionAccessStrategy.class.getName());
    private final EntityRegionImpl region;

    BaseEntityRegionAccessStrategy(EntityRegionImpl region) {
        this.region = region;
    }

    public EntityRegion getRegion() {
        return this.region;
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return this.putFromLoad(key, value, 0L, version);
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        return true;
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        return false;
    }

    protected BaseGeneralDataRegion getInternalRegion() {
        return this.region;
    }

    protected boolean isDefaultMinimalPutOverride() {
        return this.region.getSettings().isMinimalPutsEnabled();
    }
}