package com.dc.hibernate.cache.strategy;


import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.NaturalIdRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

class BaseNaturalIdRegionAccessStrategy extends BaseRegionAccessStrategy implements NaturalIdRegionAccessStrategy {
    private final NaturalIdRegionImpl region;

    protected BaseGeneralDataRegion getInternalRegion() {
        return this.region;
    }

    protected boolean isDefaultMinimalPutOverride() {
        return this.region.getSettings().isMinimalPutsEnabled();
    }

    public NaturalIdRegion getRegion() {
        return this.region;
    }

    public boolean insert(Object key, Object value) throws CacheException {
        return this.putFromLoad(key, value, 0L, (Object)null);
    }

    public boolean afterInsert(Object key, Object value) throws CacheException {
        return false;
    }

    public boolean update(Object key, Object value) throws CacheException {
        return this.putFromLoad(key, value, 0L, (Object)null);
    }

    public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
        return false;
    }

    BaseNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        this.region = region;
    }
}