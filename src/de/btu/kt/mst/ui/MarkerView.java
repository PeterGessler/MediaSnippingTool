package de.btu.kt.mst.ui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.btu.kt.mst.container.Marker;

public class MarkerView {

	private JFrame markerEditframeParent = null;
	private List<Marker> markers;
	private TableModel tableModel;
	String[] tableColumnNames = { "Id", "StartTime", "EndTime" };
	private JTable table;

	public MarkerView(List<Marker> markers) {

		this.markers = markers;

		// Create and set up the window.
		markerEditframeParent = new JFrame("Marker Editor");
		markerEditframeParent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panelContent = new JPanel(new GridLayout(0, 1));

		tableModel = new TableModel(tableColumnNames, 0) {
			Class[] types = { Integer.class, String.class, String.class };

			// making sure that it returns boolean.class.
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};

		table = new JTable(tableModel);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
        table.setDefaultRenderer(String.class, rightRenderer);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment( JLabel.LEFT );
        table.setDefaultRenderer(Integer.class, leftRenderer);
		table.setRowSelectionAllowed(true);
		panelContent.add(new JScrollPane(table));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		for (Marker marker : this.markers) {

			Object[] data = {
					marker.getMarkerId(),
					(formatTime(marker.getStartTime()).getMinutes() + ":"
							+ formatTime(marker.getStartTime()).getSeconds()
							+ ":" + formatTime(marker.getStartTime())
							.getMilliSeconds()),
					(formatTime(marker.getEndTime()).getMinutes() + ":"
							+ formatTime(marker.getEndTime()).getSeconds()
							+ ":" + formatTime(marker.getEndTime())
							.getMilliSeconds()) };
			tableModel.addRow(data);
		}

		// Set up the content pane.
		panelContent.setOpaque(true); // content panes must be opaque
		markerEditframeParent.setContentPane(panelContent);

		showMarkereditorFrame();
	}

	private void showMarkereditorFrame() {

		// Display the window.
		markerEditframeParent.pack();
		markerEditframeParent.setVisible(true);
	}

	// format to String
	private TimeView formatTime(double time) {

		int minutes = (int) (time / 60);
		int seconds = (int) (time % 60);

		long iPart = (long) time;
		int milliSeconds = (int) ((time - iPart) * 100);

		return (new TimeView(minutes, seconds, milliSeconds));
	}

	// format to double
	private double deformatTime(String minutes, String seconds,
			String milliSeconds) {

		int inSeconds = (Integer.valueOf(minutes) * 60)
				+ Integer.valueOf(seconds);
		double milliSec = Double.valueOf(milliSeconds) / 100;
		double deformatedTime = inSeconds + milliSec;
		return deformatedTime;
	}

}
