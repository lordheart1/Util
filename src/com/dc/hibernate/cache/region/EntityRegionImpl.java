package com.dc.hibernate.cache.region;

import com.dc.hibernate.cache.strategy.NonstrictReadWriteEntityRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadOnlyEntityRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadWriteEntityRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.TransactionalEntityRegionAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public
class EntityRegionImpl extends BaseTransactionalDataRegion implements EntityRegion {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, EntityRegionImpl.class.getName());
    private final Settings settings;

    public EntityRegionImpl(String name, CacheDataDescription metadata, Settings settings) {
        super(name, metadata);
        this.settings = settings;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        switch(accessType) {
            case READ_ONLY:
                if (this.getCacheDataDescription().isMutable()) {
                    LOG.warnf("read-only cache configured for mutable entity [ %s ]", this.getName());
                }

                return new ReadOnlyEntityRegionAccessStrategy(this);
            case READ_WRITE:
                return new ReadWriteEntityRegionAccessStrategy(this);
            case NONSTRICT_READ_WRITE:
                return new NonstrictReadWriteEntityRegionAccessStrategy(this);
            case TRANSACTIONAL:
                return new TransactionalEntityRegionAccessStrategy(this);
            default:
                throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }
}
