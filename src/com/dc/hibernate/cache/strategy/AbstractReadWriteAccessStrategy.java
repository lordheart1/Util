package com.dc.hibernate.cache.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractReadWriteAccessStrategy extends BaseRegionAccessStrategy {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, AbstractReadWriteAccessStrategy.class.getName());
    private final UUID uuid = UUID.randomUUID();
    private final AtomicLong nextLockId = new AtomicLong();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    protected java.util.concurrent.locks.Lock readLock;
    protected java.util.concurrent.locks.Lock writeLock;

    AbstractReadWriteAccessStrategy() {
        this.readLock = this.reentrantReadWriteLock.readLock();
        this.writeLock = this.reentrantReadWriteLock.writeLock();
    }

    public final Object get(Object key, long txTimestamp) throws CacheException {
        LOG.debugf("getting key[%s] from region[%s]", key, this.getInternalRegion().getName());

        Object var6;
        try {
            this.readLock.lock();
            AbstractReadWriteAccessStrategy.Lockable item = (AbstractReadWriteAccessStrategy.Lockable)this.getInternalRegion().get(key);
            boolean readable = item != null && item.isReadable(txTimestamp);
            if (!readable) {
                if (item == null) {
                    LOG.debugf("miss key[%s] in region[%s]", key, this.getInternalRegion().getName());
                } else {
                    LOG.debugf("hit key[%s] in region[%s], but it is unreadable", key, this.getInternalRegion().getName());
                }

                var6 = null;
                return var6;
            }

            LOG.debugf("hit key[%s] in region[%s]", key, this.getInternalRegion().getName());
            var6 = item.getValue();
        } finally {
            this.readLock.unlock();
        }

        return var6;
    }

    abstract Comparator getVersionComparator();

    public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        boolean var9;
        try {
            LOG.debugf("putting key[%s] -> value[%s] into region[%s]", key, value, this.getInternalRegion().getName());
            this.writeLock.lock();
            AbstractReadWriteAccessStrategy.Lockable item = (AbstractReadWriteAccessStrategy.Lockable)this.getInternalRegion().get(key);
            boolean writeable = item == null || item.isWriteable(txTimestamp, version, this.getVersionComparator());
            if (writeable) {
                LOG.debugf("putting key[%s] -> value[%s] into region[%s] success", key, value, this.getInternalRegion().getName());
                this.getInternalRegion().put(key, new AbstractReadWriteAccessStrategy.Item(value, version, this.getInternalRegion().nextTimestamp()));
                var9 = true;
                return var9;
            }

            LOG.debugf("putting key[%s] -> value[%s] into region[%s] fail due to it is unwriteable", key, value, this.getInternalRegion().getName());
            var9 = false;
        } finally {
            this.writeLock.unlock();
        }

        return var9;
    }

    public final SoftLock lockItem(Object key, Object version) throws CacheException {
        AbstractReadWriteAccessStrategy.Lock var7;
        try {
            LOG.debugf("locking key[%s] in region[%s]", key, this.getInternalRegion().getName());
            this.writeLock.lock();
            AbstractReadWriteAccessStrategy.Lockable item = (AbstractReadWriteAccessStrategy.Lockable)this.getInternalRegion().get(key);
            long timeout = this.getInternalRegion().nextTimestamp() + (long)this.getInternalRegion().getTimeout();
            AbstractReadWriteAccessStrategy.Lock lock = item == null ? new AbstractReadWriteAccessStrategy.Lock(timeout, this.uuid, this.nextLockId(), version) : item.lock(timeout, this.uuid, this.nextLockId());
            this.getInternalRegion().put(key, lock);
            var7 = lock;
        } finally {
            this.writeLock.unlock();
        }

        return var7;
    }

    public final void unlockItem(Object key, SoftLock lock) throws CacheException {
        try {
            LOG.debugf("unlocking key[%s] in region[%s]", key, this.getInternalRegion().getName());
            this.writeLock.lock();
            AbstractReadWriteAccessStrategy.Lockable item = (AbstractReadWriteAccessStrategy.Lockable)this.getInternalRegion().get(key);
            if (item != null && item.isUnlockable(lock)) {
                this.decrementLock(key, (AbstractReadWriteAccessStrategy.Lock)item);
            } else {
                this.handleLockExpiry(key, item);
            }
        } finally {
            this.writeLock.unlock();
        }

    }

    private long nextLockId() {
        return this.nextLockId.getAndIncrement();
    }

    protected void decrementLock(Object key, AbstractReadWriteAccessStrategy.Lock lock) {
        lock.unlock(this.getInternalRegion().nextTimestamp());
        this.getInternalRegion().put(key, lock);
    }

    protected void handleLockExpiry(Object key, AbstractReadWriteAccessStrategy.Lockable lock) {
        LOG.expired(key);
        long ts = this.getInternalRegion().nextTimestamp() + (long)this.getInternalRegion().getTimeout();
        AbstractReadWriteAccessStrategy.Lock newLock = new AbstractReadWriteAccessStrategy.Lock(ts, this.uuid, this.nextLockId.getAndIncrement(), (Object)null);
        newLock.unlock(ts);
        this.getInternalRegion().put(key, newLock);
    }

    protected static final class Lock implements Serializable, AbstractReadWriteAccessStrategy.Lockable, SoftLock {
        private static final long serialVersionUID = 2L;
        private final UUID sourceUuid;
        private final long lockId;
        private final Object version;
        private long timeout;
        private boolean concurrent;
        private int multiplicity = 1;
        private long unlockTimestamp;

        Lock(long timeout, UUID sourceUuid, long lockId, Object version) {
            this.timeout = timeout;
            this.lockId = lockId;
            this.version = version;
            this.sourceUuid = sourceUuid;
        }

        public boolean isReadable(long txTimestamp) {
            return false;
        }

        public boolean isWriteable(long txTimestamp, Object newVersion, Comparator versionComparator) {
            if (txTimestamp > this.timeout) {
                return true;
            } else if (this.multiplicity > 0) {
                return false;
            } else {
                return this.version == null ? txTimestamp > this.unlockTimestamp : versionComparator.compare(this.version, newVersion) < 0;
            }
        }

        public Object getValue() {
            return null;
        }

        public boolean isUnlockable(SoftLock lock) {
            return this.equals(lock);
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AbstractReadWriteAccessStrategy.Lock)) {
                return false;
            } else {
                return this.lockId == ((AbstractReadWriteAccessStrategy.Lock)o).lockId && this.sourceUuid.equals(((AbstractReadWriteAccessStrategy.Lock)o).sourceUuid);
            }
        }

        public int hashCode() {
            int hash = this.sourceUuid != null ? this.sourceUuid.hashCode() : 0;
            int temp = (int)this.lockId;

            for(int i = 1; i < 2; ++i) {
                temp = (int)((long)temp ^ this.lockId >>> i * 32);
            }

            return hash + temp;
        }

        public boolean wasLockedConcurrently() {
            return this.concurrent;
        }

        public AbstractReadWriteAccessStrategy.Lock lock(long timeout, UUID uuid, long lockId) {
            this.concurrent = true;
            ++this.multiplicity;
            this.timeout = timeout;
            return this;
        }

        public void unlock(long timestamp) {
            if (--this.multiplicity == 0) {
                this.unlockTimestamp = timestamp;
            }

        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Lock Source-UUID:" + this.sourceUuid + " Lock-ID:" + this.lockId);
            return sb.toString();
        }
    }

    protected static final class Item implements Serializable, AbstractReadWriteAccessStrategy.Lockable {
        private static final long serialVersionUID = 1L;
        private final Object value;
        private final Object version;
        private final long timestamp;

        Item(Object value, Object version, long timestamp) {
            this.value = value;
            this.version = version;
            this.timestamp = timestamp;
        }

        public boolean isReadable(long txTimestamp) {
            return txTimestamp > this.timestamp;
        }

        public boolean isWriteable(long txTimestamp, Object newVersion, Comparator versionComparator) {
            return this.version != null && versionComparator.compare(this.version, newVersion) < 0;
        }

        public Object getValue() {
            return this.value;
        }

        public boolean isUnlockable(SoftLock lock) {
            return false;
        }

        public AbstractReadWriteAccessStrategy.Lock lock(long timeout, UUID uuid, long lockId) {
            return new AbstractReadWriteAccessStrategy.Lock(timeout, uuid, lockId, this.version);
        }
    }

    protected interface Lockable {
        boolean isReadable(long var1);

        boolean isWriteable(long var1, Object var3, Comparator var4);

        Object getValue();

        boolean isUnlockable(SoftLock var1);

        AbstractReadWriteAccessStrategy.Lock lock(long var1, UUID var3, long var4);
    }
}
