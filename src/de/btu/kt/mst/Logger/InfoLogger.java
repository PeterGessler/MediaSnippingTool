package de.btu.kt.mst.Logger;

//TODO: description
public class InfoLogger extends ALogger {

	private static InfoLogger singleton;

	public static InfoLogger getInstance() {

		if (singleton == null)
			return singleton;

		return new InfoLogger();
	}

	private InfoLogger() {

		logName = "InfoLog_" + System.currentTimeMillis();
		
		//TODO: create file to safe error log and store process 
	}
	
	/**
	 * Write message in info log file.
	 * @param message
	 */
	@Override
	public void log(String message, Throwable throwable) {
		
		logCondition(LOG_INFO, message, null);
	}
	
	@Override
	protected void logError(String message, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void logMessage(String message) {
		// TODO Auto-generated method stub
		
	}


}
