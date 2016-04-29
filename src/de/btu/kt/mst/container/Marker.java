package de.btu.kt.mst.container;

/**
 * 
 * @author Peter Gessler
 * @version 1.0
 * @DevelopmentDate 31.12.2015
 * @LastUpdate 03.01.2016
 * @Assignment Container class to handle single marker components.
 * 
 */

public class Marker {

	private int markerId;
	private String markerName;
	private double startTime;
	private double endTime;
	
	// Constructor
	public Marker(int markerId, String markerName, double startTime, double endTime) {
		
		this.markerId = markerId;
		this.markerName = markerName;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getMarkerId() {
		return markerId;
	}
	
	public void setMarkerId(int markerId) {
		this.markerId = markerId;
	}

	public String getMarkerName() {
		return markerName;
	}
	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}
	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	public double getEndTime() {
		return endTime;
	}
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	
}
