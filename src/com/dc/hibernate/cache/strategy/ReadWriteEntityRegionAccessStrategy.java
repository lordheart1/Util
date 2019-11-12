package com.dc.hibernate.cache.strategy;

import com.dc.hibernate.cache.region.BaseGeneralDataRegion;
import com.dc.hibernate.cache.region.EntityRegionImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;


import java.util.Comparator;

public class ReadWriteEntityRegionAccessStrategy extends AbstractReadWriteAccessStrategy implements EntityRegionAccessStrategy {
    private final EntityRegionImpl region;

    public ReadWriteEntityRegionAccessStrategy(EntityRegionImpl region) {
        this.region = region;
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return this.putFromLoad(key, value, 0L, version);
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        boolean var5;
        try {
            this.writeLock.lock();
            Lockable item = (Lockable)this.region.get(key);
            if (item != null) {
                var5 = false;
                return var5;
            }

            this.region.put(key, new Item(value, version, this.region.nextTimestamp()));
            var5 = true;
        } finally {
            this.writeLock.unlock();
        }

        return var5;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        try {
            this.writeLock.lock();
            Lockable item = (Lockable)this.region.get(key);
            if (item != null && item.isUnlockable(lock)) {
                Lock lockItem = (Lock)item;
                boolean var8;
                if (lockItem.wasLockedConcurrently()) {
                    this.decrementLock(key, lockItem);
                    var8 = false;
                    return var8;
                } else {
                    this.region.put(key, new Item(value, currentVersion, this.region.nextTimestamp()));
                    var8 = true;
                    return var8;
                }
            } else {
                this.handleLockExpiry(key, item);
                boolean var7 = false;
                return var7;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    protected BaseGeneralDataRegion getInternalRegion() {
        return this.region;
    }

    protected boolean isDefaultMinimalPutOverride() {
        return this.region.getSettings().isMinimalPutsEnabled();
    }

    Comparator getVersionComparator() {
        return this.region.getCacheDataDescription().getVersionComparator();
    }

    public EntityRegion getRegion() {
        return this.region;
    }
}
