package de.btu.kt.mst.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.btu.kt.mst.MediaSnippingTool;
import de.btu.kt.mst.container.Marker;
import de.btu.kt.mst.container.ProbandTableEntry;
import de.btu.kt.mst.container.UserFile;
import de.btu.kt.mst.cutter.MovieWriter;
import de.btu.kt.mst.cutter.TrimmerAIS;
import de.btu.kt.mst.iosystem.LoadFile;
import de.btu.kt.mst.iosystem.LoadSystem;
import de.btu.kt.mst.iosystem.MarkerFileReader;

public class ProcessTask {

	private static final double NUM_OF_CUT_DELAY = 0;
	private ProbandTableEntry proband;
	private ProcessModel processModel;
	private String markerPath;
	private List<Marker> markerList;
	private String signalPath;
	private String transliterationPath;
	private String userVideoPath;

	public ProcessTask(final ProcessHandler processHandler, ProcessModel model,
			ProbandTableEntry probandTableEntry) {

		this.proband = probandTableEntry;
		this.processModel = model;

		markerPath = proband.getMarkerTxtAbsPath();
		LoadSystem loadSystem = new LoadSystem(new LoadFile());

		UserFile userFile = (UserFile) loadSystem.load(markerPath);
		File markerFile = userFile.getFile();

		MarkerFileReader mFReader = new MarkerFileReader(markerFile);

		markerList = mFReader.getMarkers();

		buildDirectoryStructure(signalPath = createDirectoryPath("sig"));

		if (processModel.isTransliterationStateTrue())
			buildDirectoryStructure(transliterationPath = createDirectoryPath("lab"));

		if (proband.isUserVideoAvailable())
			buildDirectoryStructure(userVideoPath = createDirectoryPath("vid"));

		if (proband.isMarkerTxtAvailable()) {
			createTaskDirectories(signalPath);

			if (processModel.isTransliterationStateTrue()) {
				createTaskDirectories(transliterationPath);

				Runnable taskTrlSlices = new Runnable() {

					@Override
					public void run() {

						createTransliterations(transliterationPath);
					}
				};

				MediaSnippingTool.getInstance().throwRunnableInPool(
						taskTrlSlices);

			}

			createTaskDirectories(userVideoPath);

			processHandler.notifyObserverUpdateTextArea();

			if (proband.isUserAudioAvailable()) {

				Runnable taskAudioSlices = new Runnable() {

					@Override
					public void run() {

						processModel.addToConsoleOutput("Create user audio slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();
						
						createAudioSlices(signalPath,
								proband.getUserAudioAbsPath(), "usr.wav");
						
						processModel.addToConsoleOutput("Finished user audio slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();

					}
				};

				MediaSnippingTool.getInstance().throwRunnableInPool(
						taskAudioSlices);

			}

			if (proband.isWizardAudioAvailable()) {

				Runnable taskAudioSlices = new Runnable() {

					@Override
					public void run() {
						
						processModel.addToConsoleOutput("Create wizard audio slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();
						
						createAudioSlices(signalPath,
								proband.getWizardAudioAbsPath(), "wiz.wav");
						
						processModel.addToConsoleOutput("Finished wizard audio slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();

					}
				};

				MediaSnippingTool.getInstance().throwRunnableInPool(
						taskAudioSlices);

			}

			if (proband.isUserVideoAvailable()) {

				Runnable taskVideoSlices = new Runnable() {

					@Override
					public void run() {
						
						processModel.addToConsoleOutput("Create user video slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();
						
						createVideoSlices(proband.getUserVideoAbsPath(),
								userVideoPath);
						
						processModel.addToConsoleOutput("Finished user video slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();

					}
				};

				MediaSnippingTool.getInstance().throwRunnableInPool(
						taskVideoSlices);

			}

			if (proband.isScreenVideoAvailable()) {

				Runnable taskVideoSlices = new Runnable() {

					@Override
					public void run() {
						
						processModel.addToConsoleOutput("Create screen video slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();
						
						createVideoSlices(proband.getScreenVideoAbsPath(),
								userVideoPath);
						
						processModel.addToConsoleOutput("Finished screen video slices: " + proband.getProbandName() + " | session " + proband.getSessionId());
						processHandler.notifyObserverUpdateTextArea();

					}
				};

				MediaSnippingTool.getInstance().throwRunnableInPool(
						taskVideoSlices);

			}

		}

	}

	private String createDirectoryPath(String folder) {

		StringBuilder dirPath = new StringBuilder();
		dirPath.append(processModel.getAbsDirectoryStorePath());
		dirPath.append("\\");
		dirPath.append(folder);
		dirPath.append("\\");
		dirPath.append(processModel.getProjectPhase());
		dirPath.append("\\");
		dirPath.append(proband.getProbandName().replace("pbn_", "usr0"));
		dirPath.append("\\");
		dirPath.append("session0" + proband.getSessionId());

		return dirPath.toString();

	}

	private void buildDirectoryStructure(String folderPath) {

		File theDir = new File(folderPath);

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			boolean result = false;

			try {
				theDir.mkdirs();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				//processModel.addToConsoleOutput("Create folder: " + folderPath);

			}
		}

	}

	// create task folders in each given folder
	private void createTaskDirectories(String folderPath) {

		for (int index = 0; index < markerList.size(); index++) {

			StringBuilder taskPath = new StringBuilder();
			taskPath.append(folderPath);
			taskPath.append("\\");
			if (markerList.get(index).getMarkerId() < 10)
				taskPath.append("task0" + markerList.get(index).getMarkerId());
			else
				taskPath.append("task" + markerList.get(index).getMarkerId());

			buildDirectoryStructure(taskPath.toString());
		}

	}

	private void createTransliterations(String trlFolderPath) {

		StringBuilder taskPath;
		StringBuilder fileName;

		for (int index = 0; index < markerList.size(); index++) {

			fileName = new StringBuilder();
			String[] signalPath = trlFolderPath.split("\\\\");

			fileName.append(signalPath[signalPath.length - 3].replace("phase",
					""));
			fileName.append("_");
			fileName.append(signalPath[signalPath.length - 2]
					.replace("usr", ""));
			fileName.append("_");
			fileName.append(signalPath[signalPath.length - 1].replace(
					"session", ""));
			fileName.append("_");

			taskPath = new StringBuilder();
			taskPath.append(trlFolderPath);
			taskPath.append("\\");
			if (markerList.get(index).getMarkerId() < 10) {
				taskPath.append("task0" + markerList.get(index).getMarkerId());

				fileName.append("0" + markerList.get(index).getMarkerId());

			} else {
				taskPath.append("task" + markerList.get(index).getMarkerId());

				fileName.append(markerList.get(index).getMarkerId());

			}

			taskPath.append("\\");

			fileName.append("_");

			String trlUser =  fileName.toString() + "001-usr.par";
			String trlWizard = fileName.toString() + "002-wiz.par";
			

			//taskPath.append(fileName.toString());

			File trlUsrFile = new File(taskPath.toString() + trlUser);
			File trlWizFile = new File(taskPath.toString() + trlWizard);

			if (!trlUsrFile.exists() && !trlWizFile.exists()) {
				try {
					trlUsrFile.createNewFile();
					trlWizFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (processModel.isOverrideDataState())
				try {
					trlUsrFile.createNewFile();
					trlWizFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

	}

	private void createAudioSlices(String signalFolderPath,
			String pathToOriginal, String speaker) {

		
		AudioInputStream newAudioFile = null;
		try {
			newAudioFile = AudioSystem.getAudioInputStream(new File(
					pathToOriginal));
			newAudioFile = new TrimmerAIS(newAudioFile.getFormat(),
					newAudioFile);

			StringBuilder taskPath;
			StringBuilder fileName;

			for (int index = 0; index < markerList.size(); index++) {

				fileName = new StringBuilder();
				String[] signalPath = signalFolderPath.split("\\\\");

				fileName.append(signalPath[signalPath.length - 3].replace(
						"phase", ""));
				fileName.append("_");
				fileName.append(signalPath[signalPath.length - 2].replace(
						"usr", ""));
				fileName.append("_");
				fileName.append(signalPath[signalPath.length - 1].replace(
						"session", ""));
				fileName.append("_");

				taskPath = new StringBuilder();
				taskPath.append(signalFolderPath);
				taskPath.append("\\");
				if (markerList.get(index).getMarkerId() < 10) {
					taskPath.append("task0"
							+ markerList.get(index).getMarkerId());

					fileName.append("0" + markerList.get(index).getMarkerId());

				} else {
					taskPath.append("task"
							+ markerList.get(index).getMarkerId());

					fileName.append(markerList.get(index).getMarkerId());

				}

				taskPath.append("\\");

				fileName.append("_");

				if (pathToOriginal.contains("Wizard")) {
					((TrimmerAIS) newAudioFile)
							.startSliceProcess(
									(long) ((markerList.get(index)
											.getStartTime() * 1000) + NUM_OF_CUT_DELAY),
									(long) ((markerList.get(index).getEndTime() * 1000) + NUM_OF_CUT_DELAY));

					fileName.append("002");
				}

				if (pathToOriginal.contains("User")) {
					((TrimmerAIS) newAudioFile)
							.startSliceProcess(
									(long) ((markerList.get(index)
											.getStartTime() * 1000) - NUM_OF_CUT_DELAY),
									(long) ((markerList.get(index).getEndTime() * 1000) - NUM_OF_CUT_DELAY));

					fileName.append("001");
				}

				fileName.append("-");
				taskPath.append(fileName.toString() + speaker);

				File checkFile = new File(taskPath.toString());

				if (!checkFile.exists()) {

					AudioSystem.write(newAudioFile, AudioFileFormat.Type.WAVE,
							new File(taskPath.toString()));

				} else if (processModel.isOverrideDataState())
					AudioSystem.write(newAudioFile, AudioFileFormat.Type.WAVE,
							new File(taskPath.toString()));
			}

		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (newAudioFile != null)
				try {
					newAudioFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	private void createVideoSlices(String srcFolderPath, String destFolderPath) {

		List<String> markerName = new ArrayList<String>();
		List<Double> startTime = new ArrayList<Double>();
		List<Double> endTime = new ArrayList<Double>();

		StringBuilder taskPath;
		StringBuilder fileName;

		for (int index = 0; index < markerList.size(); index++) {

			fileName = new StringBuilder();
			String[] signalPath = destFolderPath.split("\\\\");

			fileName.append(signalPath[signalPath.length - 3].replace("phase",
					""));
			fileName.append("_");
			fileName.append(signalPath[signalPath.length - 2]
					.replace("usr", ""));
			fileName.append("_");
			fileName.append(signalPath[signalPath.length - 1].replace(
					"session", ""));
			fileName.append("_");

			taskPath = new StringBuilder();
			if (markerList.get(index).getMarkerId() < 10) {
				taskPath.append("task0" + markerList.get(index).getMarkerId());

				fileName.append("0" + markerList.get(index).getMarkerId());
			} else {
				taskPath.append("task" + markerList.get(index).getMarkerId());

				fileName.append(markerList.get(index).getMarkerId());
			}

			taskPath.append("\\");
			fileName.append("_");

			if (srcFolderPath.contains("User")) {
				fileName.append("001-usr");
			}
			if (srcFolderPath.contains("Screen")) {
				fileName.append("002-wiz");
			}

			markerName.add((taskPath.toString() + fileName.toString()));
			startTime.add(markerList.get(index).getStartTime());
			endTime.add(markerList.get(index).getEndTime());
		}

		Double[] startTimes = startTime.toArray(new Double[startTime.size()]);
		Double[] endTimes = endTime.toArray(new Double[endTime.size()]);
		String[] markerNames = markerName
				.toArray(new String[markerName.size()]);

		new MovieWriter(processModel, startTimes, endTimes, markerNames, srcFolderPath,
				destFolderPath);

	}

}
