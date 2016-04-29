package de.btu.kt.mst.iosystem;

import de.btu.kt.mst.container.AFileFormat;

public class LoadSystem {

	
	ILoadStrategy loadStrategy;
	
	public LoadSystem(ILoadStrategy loadStrategy) {
		
		this.loadStrategy = loadStrategy;
	}
	
	/**
	 * Set new load strategy like LoadTextFile, LoadMovie or LoadAudio
	 * 
	 * @param loadStrategy
	 */
	public void setLoadStrategy(ILoadStrategy loadStrategy) {
		this.loadStrategy = loadStrategy;
	}
	
	public AFileFormat load(String absolutePath) {
		return loadStrategy.loadFile(absolutePath);
	}
}
