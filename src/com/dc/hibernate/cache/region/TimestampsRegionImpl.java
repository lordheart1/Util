package com.dc.hibernate.cache.region;


import com.dc.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TimestampsRegionImpl extends BaseGeneralDataRegion implements TimestampsRegion {

    private static final CoreMessageLogger LOG = (CoreMessageLogger) Logger.getMessageLogger(CoreMessageLogger.class, BaseGeneralDataRegion.class.getName());

    private static Map<Object,Object> CACHE = new HashMap<Object,Object>();
    private Properties props;


    public TimestampsRegionImpl(String regionName, Properties props) {
        super(regionName);
        this.props = props;
    }

    public Object get(Object key) throws CacheException {
        LOG.debugf("Cache[%s] lookup : key[%s]", this.getName(), key);
        if (key == null) {
            return null;
        } else {
            Object result = CACHE.get(key);
            if (result != null) {
                LOG.debugf("Cache[%s] hit: %s", this.getName(), key);
            }

            return result;
        }
    }

    public void put(Object key, Object value) throws CacheException {
        LOG.debugf("Caching[%s] : [%s] -> [%s]", this.getName(), key, value);
        if (key != null && value != null) {
            CACHE.put(key, value);
        } else {
            LOG.debug("Key or Value is null");
        }
    }

    public void evict(Object key) throws CacheException {
        LOG.debugf("Evicting[%s]: %s", this.getName(), key);
        if (key == null) {
            LOG.debug("Key is null");
        } else {
            CACHE.remove(key);
        }
    }

    public void evictAll() throws CacheException {
        LOG.debugf("evict cache[%s]", this.getName());
        CACHE = new HashMap<Object,Object>();
    }
}
