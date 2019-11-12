package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.EntityRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public class ReadOnlyEntityRegionAccessStrategy extends BaseEntityRegionAccessStrategy {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, ReadOnlyEntityRegionAccessStrategy.class.getName());

    public ReadOnlyEntityRegionAccessStrategy(EntityRegionImpl region) {
        super(region);
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        this.getInternalRegion().put(key, value);
        return true;
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.evict(key);
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        LOG.invalidEditOfReadOnlyItem(key);
        throw new UnsupportedOperationException("Can't write to a readonly object");
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        LOG.invalidEditOfReadOnlyItem(key);
        throw new UnsupportedOperationException("Can't write to a readonly object");
    }
}
