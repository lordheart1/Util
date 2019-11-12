package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.NaturalIdRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;


import java.util.Comparator;

public
class ReadWriteNaturalIdRegionAccessStrategy extends AbstractReadWriteAccessStrategy implements NaturalIdRegionAccessStrategy {
    private final NaturalIdRegionImpl region;

    public ReadWriteNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        this.region = region;
    }

    public boolean insert(Object key, Object value) throws CacheException {
        return false;
    }

    public boolean update(Object key, Object value) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value) throws CacheException {
        boolean var4;
        try {
            this.writeLock.lock();
            Lockable item = (Lockable)this.region.get(key);
            if (item == null) {
                this.region.put(key, new Item(value, (Object)null, this.region.nextTimestamp()));
                var4 = true;
                return var4;
            }

            var4 = false;
        } finally {
            this.writeLock.unlock();
        }

        return var4;
    }

    public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
        boolean var5;
        try {
            this.writeLock.lock();
            Lockable item = (Lockable)this.region.get(key);
            if (item != null && item.isUnlockable(lock)) {
                Lock lockItem = (Lock)item;
                boolean var6;
                if (lockItem.wasLockedConcurrently()) {
                    this.decrementLock(key, lockItem);
                    var6 = false;
                    return var6;
                }

                this.region.put(key, new Item(value, (Object)null, this.region.nextTimestamp()));
                var6 = true;
                return var6;
            }

            this.handleLockExpiry(key, item);
            var5 = false;
        } finally {
            this.writeLock.unlock();
        }

        return var5;
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

    public NaturalIdRegion getRegion() {
        return this.region;
    }
}
