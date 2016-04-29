package de.btu.kt.mst.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultCaret;

import de.btu.kt.mst.MediaSnippingTool;
import de.btu.kt.mst.model.IModelObserver;
import de.btu.kt.mst.model.ProcessHandler;
import de.btu.kt.mst.model.ProcessModel;

public class MainGUI extends JFrame implements IModelObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4095597496107386250L;
	private TableModel tableModel;
	private ProcessHandler processHandler;

	private String className;
	String[] tableColumnNames = { "Select", "User", "Session", "Marker",
			"UserAudio", "WizardAudio", "UserVideo", "ScreenVideo" };

	Object[][] tableRowData = {};
	String[] projectPhase = { "phase01", "phase02", "phase03" };
	private JTable table;

	private JPanel topPanelMain = null;
	private JPanel bottomPanelMain = null;
	private JPanel leftPanelTop = null;
	private JPanel rightPanelTop = null;

	JTextArea consoleArea = new JTextArea();
	private ItemListener trlListener;

	public MainGUI(String guiName, ProcessHandler processHandler) {
		super(guiName);

		className = guiName;
		this.processHandler = processHandler;
		processHandler.addModelObserver(this);
		buildMainUIComponents();

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				MediaSnippingTool.getInstance().shutdownThreadPool();
				System.out.println("ExecutorService shutdown");
				System.exit(0);
			}
		});
	}

	// structure to build all
	private void buildMainUIComponents() {
		add(buildNorthView(), BorderLayout.NORTH);
		add(buildSouthView(), BorderLayout.SOUTH);

	}

	// structure to build all in top of console
	private Component buildNorthView() {

		topPanelMain = new JPanel(new BorderLayout());
		topPanelMain.setPreferredSize(new Dimension(1024, 512));

		topPanelMain.add(buildLeftPanelTop(), BorderLayout.WEST);
		topPanelMain.add(buildRightPanelTop(), BorderLayout.EAST);

		return topPanelMain;
	}

	// structure to build button panel
	private JPanel buildLeftPanelTop() {

		leftPanelTop = new JPanel(new GridLayout(0, 1));
		leftPanelTop.setPreferredSize(new Dimension(170, 512));
		leftPanelTop.setBorder(BorderFactory.createLineBorder(Color.darkGray));

		createCrtlButtons();

		return leftPanelTop;
	}

	// structure to build table panel
	private JPanel buildRightPanelTop() {

		rightPanelTop = new JPanel(new BorderLayout());
		rightPanelTop.setPreferredSize(new Dimension(854, 512));
		tableModel = new TableModel(tableColumnNames, 0) {
			Class[] types = { Boolean.class, String.class, Integer.class,
					String.class, String.class, String.class, String.class,
					String.class };

			// making sure that it returns boolean.class.
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};

		table = new JTable(tableModel);
		table.setRowSelectionAllowed(true);
		rightPanelTop.add(new JScrollPane(table));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		return rightPanelTop;

	}

	// structure to build console panel
	private Component buildSouthView() {

		bottomPanelMain = new JPanel(new BorderLayout());
		bottomPanelMain.setPreferredSize(new Dimension(1024, 256));
		consoleArea.setSize(new Dimension(1024, 256));
		DefaultCaret caret = (DefaultCaret)consoleArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane consoleAreaScroll = new JScrollPane(consoleArea);
		bottomPanelMain.add(consoleAreaScroll);

		return bottomPanelMain;
	}

	// create buttons in gui
	private void createCrtlButtons() {

		JButton deletePbnEntries = new JButton(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2866225952874603108L;

			@Override
			public void actionPerformed(ActionEvent e) {

				processHandler.deleteAllEntries();

			}
		});
		deletePbnEntries.setText("Delete Entries");

		leftPanelTop.add(deletePbnEntries);

		JButton loadDirectoryBtn = new JButton(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2866225952874603108L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser("Ordner wählen");

				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int windowFeedback = chooser.showOpenDialog(null);

				if (windowFeedback == JFileChooser.APPROVE_OPTION) {

					processHandler.clientChooseLoadDirectory(chooser
							.getSelectedFile());
				}

			}
		});
		loadDirectoryBtn.setText("Load Dir");

		leftPanelTop.add(loadDirectoryBtn);

		JButton storeDirectoryBtn = new JButton(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2866225952874603108L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser("Ordner wählen");

				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int windowFeedback = chooser.showOpenDialog(null);

				if (windowFeedback == JFileChooser.APPROVE_OPTION) {

					processHandler.clientChooseStoreDirectory(chooser
							.getSelectedFile());
				}

			}
		});
		storeDirectoryBtn.setText("Store Dir");

		leftPanelTop.add(storeDirectoryBtn);

		final JComboBox<Object> phaseList = new JComboBox<Object>(projectPhase);
		phaseList.setSelectedIndex(0);
		phaseList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				processHandler.setProjectPhase(phaseList.getSelectedItem());
			}
		});

		leftPanelTop.add(phaseList);

		// JLabel transliterationLabel = new JLabel();
		final JCheckBox trlCheckBox = new JCheckBox("Create\nTransliterations",
				false);

		trlCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (trlCheckBox.isSelected()) {

					processHandler.setCreateTransliterationsState(true);
				} else {
					processHandler.setCreateTransliterationsState(false);
				}

			}
		});

		leftPanelTop.add(trlCheckBox);

		// allow override all data
		final JCheckBox overrideCheckBox = new JCheckBox("Override",
				false);

		overrideCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (overrideCheckBox.isSelected()) {

					processHandler.setOverrideDataState(true);
				} else {
					processHandler.setOverrideDataState(false);
				}

			}
		});

		leftPanelTop.add(overrideCheckBox);

		JButton startCutProcessBtn = new JButton(new AbstractAction() {

			List<Integer> processNumbers = new ArrayList<Integer>();

			/**
			 * 
			 */
			private static final long serialVersionUID = 2866225952874603108L;

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int index = 0; index < tableModel.getRowCount(); index++) {

					if ((Boolean) tableModel.getValueAt(index, 0) == true)
						processNumbers.add(index);

				}

				processHandler.startCutProcess(processNumbers);

			}
		});
		startCutProcessBtn.setText("Start Cut Process");

		leftPanelTop.add(startCutProcessBtn);

	}

	@Override
	public String getName() {
		return className;
	}

	@Override
	public synchronized void updateProbandEntries(final ProcessModel model) {

		// delete all
		if (tableModel.getRowCount() > 0) {
			for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
				tableModel.removeRow(i);
			}
		}

		if (!(model.getProbandEntries() == null)) {
			if (!model.getProbandEntries().isEmpty()) {

				for (int index = 0; index < model.getProbandEntries().size(); index++) {

					String usrName = model.getProbandEntries().get(index)
							.getProbandName();
					int sessionId = model.getProbandEntries().get(index)
							.getSessionId();
					boolean usrAudioAvailable = model.getProbandEntries()
							.get(index).isUserAudioAvailable();
					boolean wizAudioAvailable = model.getProbandEntries()
							.get(index).isWizardAudioAvailable();
					boolean markerAvailable = model.getProbandEntries()
							.get(index).isMarkerTxtAvailable();
					boolean usrVideo = model.getProbandEntries().get(index)
							.isUserVideoAvailable();
					boolean screenVideo = model.getProbandEntries().get(index)
							.isScreenVideoAvailable();

					boolean selected = usrAudioAvailable && wizAudioAvailable
							&& markerAvailable && usrVideo && screenVideo;

					Object[] data = { selected, usrName, sessionId,
							markerAvailable, usrAudioAvailable,
							wizAudioAvailable, usrVideo, screenVideo };

					tableModel.addRow(data);

					table.setDefaultRenderer(Object.class,
							new DefaultTableCellRenderer() {
								@Override
								public Component getTableCellRendererComponent(
										JTable table, Object value,
										boolean isSelected, boolean hasFocus,
										int row, int column) {
									final Component c = super
											.getTableCellRendererComponent(
													table, value, isSelected,
													hasFocus, row, column);
									c.setBackground((Boolean.valueOf(table
											.getValueAt(row, 3).toString())
											&& Boolean.valueOf(table
													.getValueAt(row, 4)
													.toString())
											&& Boolean.valueOf(table
													.getValueAt(row, 5)
													.toString())
											&& Boolean.valueOf(table
													.getValueAt(row, 6)
													.toString()) && Boolean
											.valueOf(table.getValueAt(row, 7)
													.toString())) ? Color.GREEN
											: Color.RED);
									return c;
								}
							});

				}

				tableModel.fireTableRowsUpdated(0, model.getProbandEntries()
						.size());

				Action changeMarks = new AbstractAction() {
					/**
				 * 
				 */
					private static final long serialVersionUID = 974903618270021782L;

					public void actionPerformed(ActionEvent e) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());

						processHandler.openMarkerView(modelRow);
					}
				};

				ButtonColumn buttonColumn = new ButtonColumn(table,
						changeMarks, 2);
				buttonColumn.setMnemonic(KeyEvent.VK_D);
			}

		}

	}

	@Override
	public void updateTextArea(ProcessModel model) {
		consoleArea.setText(model.getConsoleOutput());

	}

}
