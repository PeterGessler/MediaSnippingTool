package de.btu.kt.mst.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.btu.kt.mst.MediaSnippingTool;
import de.btu.kt.mst.container.ProbandEntryList;
import de.btu.kt.mst.container.UserFile;
import de.btu.kt.mst.iosystem.LoadDirectoryList;
import de.btu.kt.mst.iosystem.LoadFile;
import de.btu.kt.mst.iosystem.LoadSystem;
import de.btu.kt.mst.iosystem.MarkerFileReader;
import de.btu.kt.mst.ui.MarkerView;

public class ProcessHandler {

	private LoadSystem loadSystem;
	private List<IModelObserver> modelObserver;
	private ProcessModel model;

	public ProcessHandler(ProcessModel model) {

		this.model = model;
		modelObserver = new ArrayList<IModelObserver>();
		loadSystem = new LoadSystem(new LoadDirectoryList());
	}

	public void addModelObserver(IModelObserver observer) {
		modelObserver.add(observer);
	}

	public void deleteModelObserver(IModelObserver observer) {
		for (IModelObserver observerFromList : modelObserver) {

			if (observer.getName().equals(observerFromList.getName())) {
				modelObserver.remove(observerFromList);
			}
		}
	}

	// notify observer if model state changed
	private void notifyObserver() {

		for (IModelObserver iModelObserver : modelObserver) {
			iModelObserver.updateProbandEntries(model);
		}
	}

	public void notifyObserverUpdateTextArea() {

		for (IModelObserver iModelObserver : modelObserver) {
			iModelObserver.updateTextArea(model);
		}
	}

	/**
	 * Store all available file properties of given directory in process model.
	 * 
	 * @param selectedFile
	 */
	public void clientChooseLoadDirectory(File selectedFile) {

		model.setProbandEntries((ProbandEntryList) loadSystem.load(selectedFile
				.getAbsolutePath()));
		model.addToConsoleOutput("Load all files from "
				+ selectedFile.getAbsolutePath());
		notifyObserver();
		notifyObserverUpdateTextArea();
	}

	public void clientChooseStoreDirectory(File selectedFile) {

		model.setAbsDirectoryStorePath(selectedFile.getAbsolutePath());
		model.addToConsoleOutput("StoreDirectory path: "
				+ selectedFile.getAbsolutePath());
		notifyObserverUpdateTextArea();
	}

	public void deleteAllEntries() {

		model.deleteProbandEntries();
		model.addToConsoleOutput("All proband entries deleted!");
		notifyObserver();
		notifyObserverUpdateTextArea();

	}

	public void setProjectPhase(Object object) {
		model.setProjectPhase((String) object);
		model.addToConsoleOutput("Set project phase to " + (String) object);
		notifyObserverUpdateTextArea();
	}

	public void setCreateTransliterationsState(boolean state) {

		model.setCreateTransliterationState(state);

		if (state)
			model.addToConsoleOutput("Create transliterations files to each task!");
		else
			model.addToConsoleOutput("Create no transliterations files!");

		notifyObserverUpdateTextArea();

	}
	
	
	public void setOverrideDataState(boolean overrideDataState) {

		model.setOverrideDataState(overrideDataState);

		if (overrideDataState)
			model.addToConsoleOutput("Override all existing data");
		else
			model.addToConsoleOutput("Override is not allowed");

		notifyObserverUpdateTextArea();

	}

	public void openMarkerView(int probandEntryNumber) {

		String markerPath = model.getProbandEntries().get(probandEntryNumber)
				.getMarkerTxtAbsPath();
		loadSystem.setLoadStrategy(new LoadFile());

		UserFile userFile = (UserFile) loadSystem.load(markerPath);
		File markerFile = userFile.getFile();

		MarkerFileReader mFReader = new MarkerFileReader(markerFile);

		new MarkerView(mFReader.getMarkers());
	}

	public void startCutProcess(List<Integer> processNumbers) {

		/*
		 * for (ProbandTableEntry proband : model.getProbandEntries()) {
		 * 
		 * System.out.println(proband.getProbandName());
		 * System.out.println(proband.getUserAudioAbsPath());
		 * System.out.println(proband.getWizardAudioAbsPath());
		 * System.out.println(proband.getUserVideoAbsPath());
		 * System.out.println(proband.getScreenVideoAbsPath());
		 * System.out.println(proband.getMarkerTxtAbsPath());
		 * 
		 * System.out.println("------------------------------------------"); }
		 */

		model.addToConsoleOutput("Start cut process!");
		notifyObserverUpdateTextArea();

		for (final Integer index : processNumbers) {

			Runnable processTask = new Runnable() {

				@Override
				public void run() {


					ProcessTask process = new ProcessTask(getProceHandler(),
							model, model.getProbandEntries().get(index));

				}
			};

			MediaSnippingTool.getInstance().throwRunnableInPool(processTask);
		}

	}

	private ProcessHandler getProceHandler() {
		return this;
	}

}
