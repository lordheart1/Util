package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.NaturalIdRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

public
class ReadOnlyNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, ReadOnlyNaturalIdRegionAccessStrategy.class.getName());

    public ReadOnlyNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super(region);
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.evict(key);
    }
}
