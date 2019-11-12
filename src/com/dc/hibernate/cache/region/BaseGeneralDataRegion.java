package com.dc.hibernate.cache.region;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.GeneralDataRegion;
import org.hibernate.internal.CoreMessageLogger;

import org.jboss.logging.Logger;

public class BaseGeneralDataRegion extends BaseRegion implements GeneralDataRegion {
    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, BaseGeneralDataRegion.class.getName());

    public BaseGeneralDataRegion(String name) {
        super(name);
    }

    public Object get(Object key) throws CacheException {
        LOG.debugf("Cache[%s] lookup : key[%s]", this.getName(), key);
        if (key == null) {
            return null;
        } else {
            Object result = this.getCache().get(key);
            if (result != null) {
                LOG.debugf("Cache[%s] hit: %s", this.getName(), key);
            }

            return result;
        }
    }

    public void put(Object key, Object value) throws CacheException {
        LOG.debugf("Caching[%s] : [%s] -> [%s]", this.getName(), key, value);
        if (key != null && value != null) {
            this.getCache().put(key, value);
        } else {
            LOG.debug("Key or Value is null");
        }
    }

    public void evict(Object key) throws CacheException {
        LOG.debugf("Evicting[%s]: %s", this.getName(), key);
        if (key == null) {
            LOG.debug("Key is null");
        } else {
            this.getCache().evict(key);
        }
    }

    public void evictAll() throws CacheException {
        LOG.debugf("evict cache[%s]", this.getName());
        this.getCache().clear();
    }
}
