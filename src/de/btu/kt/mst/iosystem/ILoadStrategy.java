package de.btu.kt.mst.iosystem;

import de.btu.kt.mst.container.AFileFormat;

public interface ILoadStrategy {

	AFileFormat loadFile(String absolutePath);

}
