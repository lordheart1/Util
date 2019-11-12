package com.dc.hibernate.cache.region;

import org.hibernate.cache.spi.QueryResultsRegion;

import java.util.Properties;

public class QueryResultsRegionImpl extends BaseGeneralDataRegion implements QueryResultsRegion {

    private Properties prop;
    private String regionName;

    public QueryResultsRegionImpl(String regionName,Properties props) {

        super(regionName);
        this.prop = props;
    }

}
