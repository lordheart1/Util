package com.dc.util.lbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dc.util.jsonfile.WelJsonDao;
import com.dc.util.lbs.bean.LngLatRange;


@Repository("lbsUtil")
public class LbsUtil {

	@Resource(name="welJsonDao")
	private WelJsonDao welJsonDao;
	
	@Value("${lbs_range}")
	private String lbsJsonFile;
	
	private static List<LngLatRange> RANGES;
	
	public LngLatRange getLatRange(double lat,int distance) {
	
		if(RANGES == null) {
			
			createRangeConfig();
		}
		
		for (LngLatRange range : RANGES) {
			
			if(range.getDistance() == distance) {
				
				if(range.getLatMax() > lat && range.getLatMin() <= lat ) {
					
					return range;
				}
			}
		}
		
		return null;
	}
	
	synchronized private  void createRangeConfig() {
		
		if(RANGES != null) {
			
			return;
		}
		
		JSONArray jsonArray = this.welJsonDao.getJsonArray(this.lbsJsonFile, true);
		
		if(jsonArray == null) {
			
			return;
		}
		
		Iterator<JSONObject> iter = jsonArray.iterator();
		
		int length = jsonArray.size();
		
		List<LngLatRange> ranges = new ArrayList<LngLatRange>(length);
		
		while(iter.hasNext()) {
			
			JSONObject jsonObj = iter.next();
			
			LngLatRange range = (LngLatRange)JSONObject.toBean(jsonObj, LngLatRange.class);
			
			ranges.add(range);
		}
		
		Collections.sort(ranges, new LngLatRangeComparator());
		
		double latMin = 0f;
		double preLatMax = 0f;
	
		int distance = 0;
		
		
		for (int i = 0; i < length; i++) {
			
			LngLatRange range = ranges.get(i);
			
			if(distance > range.getDistance()) {
				
				latMin = preLatMax;
			}
			
			range.setLatMin(latMin);
			
			distance = range.getDistance();
			preLatMax = range.getLatMax();
		}
		
		RANGES = ranges;
	}
}

class LngLatRangeComparator implements Comparator<LngLatRange> {

	@Override
	public int compare(LngLatRange o1, LngLatRange o2) {
			
		Double r2LatMax = o1.getLatMax();
		
		return r2LatMax.compareTo(o2.getLatMax());
	}	
}