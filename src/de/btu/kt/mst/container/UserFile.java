package de.btu.kt.mst.container;

import java.io.File;

public class UserFile extends AFileFormat {

	File file;
	
	public UserFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

}
