package de.btu.kt.mst.iosystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.btu.kt.mst.container.ProbandEntryList;
import de.btu.kt.mst.container.ProbandTableEntry;

public class LoadDirectoryList implements ILoadStrategy {

	private static final int NUM_OF_DATA_PER_PROBAND = 5;
	private List<ProbandTableEntry> directoryFileList;
	private List<String> fileList;
	private File isFileAvailable;

	public LoadDirectoryList() {

		fileList = new ArrayList<String>();
		directoryFileList = new ArrayList<ProbandTableEntry>();

	}

	public ProbandEntryList loadFile(String absolutePath) {

		fileList = getFileNames(fileList, Paths.get(absolutePath));

		int fileNum = 0;
		boolean isFirstEntry = true;

		ProbandTableEntry probandEntry = new ProbandTableEntry();

		for (String entry : fileList) {

			isFileAvailable = new File(entry);

			if ((fileNum % NUM_OF_DATA_PER_PROBAND == 0)) {

				if (!isFirstEntry) {
					
					directoryFileList.add(probandEntry);
					probandEntry = new ProbandTableEntry();
				}

			}
			
			isFirstEntry = false;

			fileNum++;

			// store screen video, sessionId and pbn name
			if (entry.contains("tmpScreenCapture")) {

				String[] pathSlices = entry.split("\\\\");

				probandEntry.setProbandName(pathSlices[pathSlices.length - 3]);

				if (isFileAvailable.length() > 10) {
					probandEntry.setScreenVideoAvailable(true);
					probandEntry.setScreenVideoAbsPath(entry);
				} else {
					probandEntry.setScreenVideoAbsPath("");
				}

				probandEntry.setSessionId(Integer
						.parseInt(pathSlices[pathSlices.length - 4]));

			}

			// check Session-Marker.txt properties
			if (entry.contains("Session-Marker")) {

				if (isFileAvailable.length() > 10) {
					probandEntry.setMarkerTxtAvailable(true);
					probandEntry.setMarkerTxtAbsPath(entry);
				} else {
					probandEntry.setMarkerTxtAbsPath("");
				}

			}

			// check UserTalkRaw.wav properties
			if (entry.contains("UserTalkRaw")) {

				if (isFileAvailable.length() > 10) {
					probandEntry.setUserAudioAvailable(true);
					probandEntry.setUserAudioAbsPath(entry);
				} else {
					probandEntry.setUserAudioAbsPath("");
				}

			}

			// check WizardTalkRaw.wav properties
			if (entry.contains("WizardTalkRaw")) {

				if (isFileAvailable.length() > 10) {
					probandEntry.setWizardAudioAvailable(true);
					probandEntry.setWizardAudioAbsPath(entry);
				} else {
					probandEntry.setWizardAudioAbsPath("");
				}

			}

			// check UserVideo.mov properties
			if (entry.contains("UserVideo")) {

				if (isFileAvailable.length() > 10) {
					probandEntry.setUserVideoAvailable(true);
					probandEntry.setUserVideoAbsPath(entry);
				} else {
					probandEntry.setUserVideoAbsPath("");
				}

			}

		}

		return new ProbandEntryList(directoryFileList);
	}

	/*
	 * Return list with all abs paths in dir
	 */
	private List<String> getFileNames(List<String> fileNames, Path dir) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {
				if (path.toFile().isDirectory()) {
					getFileNames(fileNames, path);
				} else {
					fileNames.add(path.toAbsolutePath().toString());
					// System.out.println(path.getFileName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileNames;
	}

	// <-------------- Test only ---------------->
	public static void main(String[] args) {

		LoadDirectoryList dirList = new LoadDirectoryList();
		ProbandEntryList probands = dirList
				.loadFile("C:\\Studium\\Projekte\\ucui\\data\\UCUI\\common");

		for (ProbandTableEntry probandTableEntry : probands.getProbandList()) {

			System.out.println(probandTableEntry.getProbandName());
			System.out.println(probandTableEntry.getSessionId());
			System.out.println(probandTableEntry.getUserAudioAbsPath());
			System.out.println(probandTableEntry.getMarkerTxtAbsPath());
			System.out.println("----------------");
		}
	}
}
