package com.dc.util.map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("mapUtil")
public class MapUtil {

	@Value("${cale_earth_radius}")
	private double earthRadius;
	
	@Value("${jl_jd}")
	private double jl_jd;
	
	@Value("${jl_wd}")
	private double jl_wd;

	private static double rad(double d) {
		return d * Math.PI / 180.0d;
	}

	public  double caleDistanceJs(double lng1, double lat1, double lng2, double lat2) {
	
		double R = this.earthRadius;
		
		double p = Math.PI / 180d;
		
		double radLat1 = lat1 * p;
		double radLat2 = lat2 * p;
       
        double a = radLat1 - radLat2; 
        double b = (lng1 - lng2) * p; 
        
        
        double r = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
        		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));

        
        return r * R;  
	}
/*
	public  double caleDistance(double jd1,double wd1, double jd2,double wd2) {
		double x, y, out;
		double PI = Math.PI;
		double R = this.r * 1e6;

		x = (jd2 - jd1) * PI * R * Math.cos(((wd1 + wd2) / 2d) * PI / 180d) / 180d;
		y = (wd2 - wd1) * PI * R / 180d;
		out = Math.hypot(x, y);
		return out / 1000d;
	}
	*/
}