package de.btu.kt.mst.container;

/**
 * 
 * @author Master
 * @date 17.04.2016
 * @version v0.01
 * @description: Instance represent proband folder input. Hold and return all values as Strings (absolute paths).<br> 
 *
 */
public class ProbandTableEntry {
	
	// all path variables of table entry
	private String probandName;
	private int sessionId;
	private String userAudioAbsPath;
	private String wizardAudioAbsPath;
	private String userVideoAbsPath;
	private String screenVideoAbsPath;
	private String markerTxtAbsPath;
	
	// to check if file is available or not
	private boolean userAudioAvailable = false;
	private boolean wizardAudioAvailable = false;
	private boolean userVideoAvailable = false;
	private boolean screenVideoAvailable = false;
	private boolean markerTxtAvailable = false;
	
	public ProbandTableEntry() {
		
	}

	public String getProbandName() {
		return probandName;
	}

	public void setProbandName(String probandName) {
		this.probandName = probandName;
	}

	public String getUserAudioAbsPath() {
		return userAudioAbsPath;
	}

	public void setUserAudioAbsPath(String userAudioAbsPath) {
		this.userAudioAbsPath = userAudioAbsPath;
	}

	public String getWizardAudioAbsPath() {
		return wizardAudioAbsPath;
	}

	public void setWizardAudioAbsPath(String wizardAudioAbsPath) {
		this.wizardAudioAbsPath = wizardAudioAbsPath;
	}

	public String getUserVideoAbsPath() {
		return userVideoAbsPath;
	}

	public void setUserVideoAbsPath(String userVideoAbsPath) {
		this.userVideoAbsPath = userVideoAbsPath;
	}

	public String getScreenVideoAbsPath() {
		return screenVideoAbsPath;
	}

	public void setScreenVideoAbsPath(String screenVideoAbsPath) {
		this.screenVideoAbsPath = screenVideoAbsPath;
	}

	public String getMarkerTxtAbsPath() {
		return markerTxtAbsPath;
	}

	public void setMarkerTxtAbsPath(String markerTxtAbsPath) {
		this.markerTxtAbsPath = markerTxtAbsPath;
	}

	public boolean isUserAudioAvailable() {
		return userAudioAvailable;
	}

	public void setUserAudioAvailable(boolean userAudioAvailable) {
		this.userAudioAvailable = userAudioAvailable;
	}

	public boolean isWizardAudioAvailable() {
		return wizardAudioAvailable;
	}

	public void setWizardAudioAvailable(boolean wizardAudioAvailable) {
		this.wizardAudioAvailable = wizardAudioAvailable;
	}

	public boolean isUserVideoAvailable() {
		return userVideoAvailable;
	}

	public void setUserVideoAvailable(boolean userVideoAvailable) {
		this.userVideoAvailable = userVideoAvailable;
	}

	public boolean isScreenVideoAvailable() {
		return screenVideoAvailable;
	}

	public void setScreenVideoAvailable(boolean screenVideoAvailable) {
		this.screenVideoAvailable = screenVideoAvailable;
	}

	public boolean isMarkerTxtAvailable() {
		return markerTxtAvailable;
	}

	public void setMarkerTxtAvailable(boolean markerTxtAvailable) {
		this.markerTxtAvailable = markerTxtAvailable;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
