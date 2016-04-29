package de.btu.kt.mst.model;

public interface IModelObserver {

	public String getName();
	
	public void updateProbandEntries(ProcessModel model);
	
	public void updateTextArea(ProcessModel model);
}
