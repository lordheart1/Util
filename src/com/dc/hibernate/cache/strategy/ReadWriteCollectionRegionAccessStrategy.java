package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.CollectionRegionImpl;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;

import java.util.Comparator;

public class ReadWriteCollectionRegionAccessStrategy extends AbstractReadWriteAccessStrategy implements CollectionRegionAccessStrategy {
    private final CollectionRegionImpl region;

    public ReadWriteCollectionRegionAccessStrategy(CollectionRegionImpl region) {
        this.region = region;
    }

    Comparator getVersionComparator() {
        return this.region.getCacheDataDescription().getVersionComparator();
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
