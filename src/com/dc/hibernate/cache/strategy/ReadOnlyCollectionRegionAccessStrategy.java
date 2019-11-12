package com.dc.hibernate.cache.strategy;


import com.dc.hibernate.cache.region.CollectionRegionImpl;

public class ReadOnlyCollectionRegionAccessStrategy extends BaseCollectionRegionAccessStrategy {
    public ReadOnlyCollectionRegionAccessStrategy(CollectionRegionImpl region) {
        super(region);
    }
}