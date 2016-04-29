package de.btu.kt.mst.ui;

public class TimeView {

	private int minutes;
	private int seconds;
	private int milliSeconds;

	public TimeView(int minutes, int seconds, int milliSeconds) {
		
		this.setMinutes(minutes);
		this.setSeconds(seconds);
		this.setMilliSeconds(milliSeconds);
	}

	public int getMinutes() {
		return minutes;
	}

	private void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	private void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMilliSeconds() {
		return milliSeconds;
	}

	private void setMilliSeconds(int milliSeconds) {
		this.milliSeconds = milliSeconds;
	}
	
}
