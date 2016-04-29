package de.btu.kt.mst.model;

import java.util.List;

import de.btu.kt.mst.container.ProbandEntryList;
import de.btu.kt.mst.container.ProbandTableEntry;

public class ProcessModel {

	private String consoleOutput = "";
	private List<ProbandTableEntry> probandEntries;
	private String absDirectoryStorePath;
	private String projectPhase;
	private boolean createTrlState = false;
	private boolean overrideDataState = false;

	public ProcessModel() {

		this.projectPhase = "phase01";
	}

	public List<ProbandTableEntry> getProbandEntries() {
		return probandEntries;
	}

	public void setProbandEntries(ProbandEntryList probandEntryList) {
		this.probandEntries = probandEntryList.getProbandList();
	}

	public String getAbsDirectoryStorePath() {
		return absDirectoryStorePath;
	}

	public void setAbsDirectoryStorePath(String absDirectoryStorePath) {
		this.absDirectoryStorePath = absDirectoryStorePath;
	}

	public String getConsoleOutput() {
		return consoleOutput;
	}

	public void addToConsoleOutput(String consoleOutput) {
		this.consoleOutput = this.consoleOutput + "\n" + consoleOutput;
	}

	public void deleteProbandEntries() {

		if (probandEntries != null)
			probandEntries.clear();

	}

	public String getProjectPhase() {
		return this.projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}
	
	public boolean isTransliterationStateTrue() {
		return createTrlState;
	}

	public void setCreateTransliterationState(boolean state) {
		createTrlState = state;

	}

	public boolean isOverrideDataState() {
		return overrideDataState;
	}

	public void setOverrideDataState(boolean overrideDataState) {
		this.overrideDataState = overrideDataState;
	}

}
