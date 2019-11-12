package com.dc.hibernate.cache;

import java.util.List;
import java.util.Map;

public interface Cache {

    public void put(Object key,Object value);

    public void evict(Object key);

    public void clear();

    public Object get(Object key);

    public boolean exists(Object key);

    public List<Object> getStatistics();

    public Map toMap();
}
