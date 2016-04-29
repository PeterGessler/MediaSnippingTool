package de.btu.kt.mst;

import java.awt.Dimension;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import de.btu.kt.mst.model.ProcessHandler;
import de.btu.kt.mst.model.ProcessModel;
import de.btu.kt.mst.ui.MainGUI;

/**
 * 
 * @author Peter Gessler
 * @version 1.0
 * @DevelopmentDate 31.12.2015
 * @LastUpdate 03.01.2016
 * @Assignment Start video editor and initialize user interface.
 * 
 */

public class MediaSnippingTool {

	private static MediaSnippingTool instance;
	
	private static final int NUM_OF_THREADS = 40;
	
	private ExecutorService processExecutor = Executors
			.newFixedThreadPool(NUM_OF_THREADS);

	public static MediaSnippingTool getInstance() {

		if (instance == null) {
			instance = new MediaSnippingTool();

		}

		return instance;
	}

	private MediaSnippingTool() {

		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProcessModel model = new ProcessModel();
		final ProcessHandler processHandler = new ProcessHandler(model);

		// start GUI
		MainGUI guiGeneral = new MainGUI("Media Snipping Tool", processHandler);
		guiGeneral.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		guiGeneral.pack();
		guiGeneral.setPreferredSize(new Dimension(1024, 768));
		guiGeneral.setVisible(true);

	}
	
	public void throwRunnableInPool(Runnable execute) {
		processExecutor.execute(execute);
	}
	
	public void shutdownThreadPool() {
		processExecutor.shutdown();
	}

	public static void main(String[] args) {
		
		MediaSnippingTool.getInstance();
	}

}
