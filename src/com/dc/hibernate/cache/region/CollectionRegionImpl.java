package com.dc.hibernate.cache.region;

import com.dc.hibernate.cache.strategy.NonstrictReadWriteCollectionRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadOnlyCollectionRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadWriteCollectionRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.TransactionalCollectionRegionAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public
class CollectionRegionImpl extends BaseTransactionalDataRegion implements CollectionRegion {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, CollectionRegionImpl.class.getName());
    private final Settings settings;

    public CollectionRegionImpl(String name, CacheDataDescription metadata, Settings settings) {
        super(name, metadata);
        this.settings = settings;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        switch(accessType) {
            case READ_ONLY:
                if (this.getCacheDataDescription().isMutable()) {
                    LOG.warnf("read-only cache configured for mutable collection [ %s ]", this.getName());
                }

                return new ReadOnlyCollectionRegionAccessStrategy(this);
            case READ_WRITE:
                return new ReadWriteCollectionRegionAccessStrategy(this);
            case NONSTRICT_READ_WRITE:
                return new NonstrictReadWriteCollectionRegionAccessStrategy(this);
            case TRANSACTIONAL:
                return new TransactionalCollectionRegionAccessStrategy(this);
            default:
                throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }
}