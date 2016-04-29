package de.btu.kt.mst.container;

import java.util.List;

public class ProbandEntryList extends AFileFormat {

	List<ProbandTableEntry> ProbandTableEntryList;
	
	public ProbandEntryList (List<ProbandTableEntry> list) {
		this.ProbandTableEntryList = list;
	}
	
	public List<ProbandTableEntry> getProbandList() {
		return ProbandTableEntryList;
	}
}
