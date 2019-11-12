package com.dc.hibernate.cache.strategy;


import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.CollectionRegionImpl;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;


class BaseCollectionRegionAccessStrategy extends BaseRegionAccessStrategy implements CollectionRegionAccessStrategy {
    private final CollectionRegionImpl region;


    BaseCollectionRegionAccessStrategy(CollectionRegionImpl region) {
        this.region = region;
    }

    protected BaseGeneralDataRegion getInternalRegion() {
        return this.region;
    }

    protected boolean isDefaultMinimalPutOverride() {
        return this.region.getSettings().isMinimalPutsEnabled();
    }

    public CollectionRegion getRegion() {
        return this.region;
    }
}
