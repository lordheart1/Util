package com.dc.hibernate.cache.region;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;


public
class BaseTransactionalDataRegion extends BaseGeneralDataRegion implements TransactionalDataRegion {
    private final CacheDataDescription metadata;

    BaseTransactionalDataRegion(String name, CacheDataDescription metadata) {
        super(name);
        this.metadata = metadata;
    }

    public CacheDataDescription getCacheDataDescription() {
        return this.metadata;
    }

    public boolean isTransactionAware() {
        return false;
    }
}
