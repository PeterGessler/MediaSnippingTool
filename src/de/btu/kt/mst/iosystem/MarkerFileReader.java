package de.btu.kt.mst.iosystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.btu.kt.mst.container.Marker;

public class MarkerFileReader {

	List<Marker> markers;
	
	public MarkerFileReader(File markerFile) {
		
		markers = new ArrayList<Marker>();
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(markerFile.getPath());

			BufferedReader buffReader = new BufferedReader(fileReader);

			String rowLine = "";
			int id = 0;

			while ((rowLine = buffReader.readLine()) != null) {
				String[] columnDetail = new String[3];
				columnDetail = rowLine.split("\t");

				Marker newMarker = null;

				// catch replace comma with dot if *.txt writed with dots
				try {

					newMarker = new Marker(id, columnDetail[2],
							Double.valueOf(columnDetail[0].replace(',', '.')),
							Double.valueOf(columnDetail[1].replace(',', '.')));
					
					markers.add(newMarker);

				} catch (NumberFormatException error) {

					newMarker = new Marker(id, columnDetail[2],
							Double.valueOf(columnDetail[0]),
							Double.valueOf(columnDetail[1]));

				}

				id++;
			}
			
			buffReader.close();
			
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Marker> getMarkers() {
		return markers;
	}

}
