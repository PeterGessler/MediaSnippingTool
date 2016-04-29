package de.btu.kt.mst.iosystem;

import java.io.File;

import de.btu.kt.mst.container.UserFile;

public class LoadFile implements ILoadStrategy {

	@Override
	public UserFile loadFile(String absolutePath) {
		
		return new UserFile(new File(absolutePath));
	}

}
