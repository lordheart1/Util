package com.dc.util.lbs.bean;

public class LngLatRange {

	private double latMax;
	private double latMin;
	
	private double latLevel;
	private double lngCorrect;
	private double latCorrect;
	private int distance;
	
	public double getLatMax() {
		return latMax;
	}
	public void setLatMax(double latMax) {
		this.latMax = latMax;
	}
	public double getLatMin() {
		return latMin;
	}
	public void setLatMin(double latMin) {
		this.latMin = latMin;
	}
	public double getLatLevel() {
		return latLevel;
	}
	public void setLatLevel(double latLevel) {
		this.latLevel = latLevel;
	}
	public double getLngCorrect() {
		return lngCorrect;
	}
	public void setLngCorrect(double lngCorrect) {
		this.lngCorrect = lngCorrect;
	}
	public double getLatCorrect() {
		return latCorrect;
	}
	public void setLatCorrect(double latCorrect) {
		this.latCorrect = latCorrect;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
}