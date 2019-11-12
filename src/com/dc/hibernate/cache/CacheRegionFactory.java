package com.dc.hibernate.cache;

import com.dc.hibernate.cache.region.*;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.*;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;
import org.springframework.util.Assert;

import java.util.Properties;

public class CacheRegionFactory implements RegionFactory {
    private static final long serialVersionUID = -1557439471733872383L;

    protected Settings settings;

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
    }

    @Override
    public void stop() {


    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    @Override
    public AccessType getDefaultAccessType() {
        return AccessType.NONSTRICT_READ_WRITE;
    }

    @Override
    public long nextTimestamp() {
        return  Timestamper.next();
    }

    @Override
    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return new EntityRegionImpl(regionName, metadata,settings);
    }

    @Override
    public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return new NaturalIdRegionImpl(regionName, metadata,settings);
    }

    @Override
    public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return new CollectionRegionImpl(regionName, metadata, settings);
    }

    @Override
    public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
        return new QueryResultsRegionImpl( regionName, properties);
    }

    @Override
    public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
        return new TimestampsRegionImpl(regionName, properties);
    }

    private static String shortRegionName(String regionName) {
        return "sb";
    }
}
