package com.dc.hibernate.cache.region;

import com.dc.hibernate.cache.strategy.NonstrictReadWriteNaturalIdRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadOnlyNaturalIdRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.ReadWriteNaturalIdRegionAccessStrategy;
import com.dc.hibernate.cache.strategy.TransactionalNaturalIdRegionAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Settings;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public
class NaturalIdRegionImpl extends BaseTransactionalDataRegion implements NaturalIdRegion {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, NaturalIdRegionImpl.class.getName());
    private final Settings settings;

    public NaturalIdRegionImpl(String name, CacheDataDescription metadata, Settings settings) {
        super(name, metadata);
        this.settings = settings;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public NaturalIdRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        switch(accessType) {
            case READ_ONLY:
                if (this.getCacheDataDescription().isMutable()) {
                    LOG.warnf("read-only cache configured for mutable collection [ %s ]", this.getName());
                }

                return new ReadOnlyNaturalIdRegionAccessStrategy(this);
            case READ_WRITE:
                return new ReadWriteNaturalIdRegionAccessStrategy(this);
            case NONSTRICT_READ_WRITE:
                return new NonstrictReadWriteNaturalIdRegionAccessStrategy(this);
            case TRANSACTIONAL:
                return new TransactionalNaturalIdRegionAccessStrategy(this);
            default:
                throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }
}
