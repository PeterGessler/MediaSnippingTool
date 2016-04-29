package de.btu.kt.mst.Logger;

//TODO: description
public class ProcessLogger extends ALogger {

	private static ProcessLogger singleton;

	public static ProcessLogger getInstance() {

		if (singleton == null)
			return singleton;

		return new ProcessLogger();
	}

	private ProcessLogger() {

		logName = "ProcessLog_" + System.currentTimeMillis();
		
		//TODO: create file to safe error log and store process 
	}
	
	/**
	 * Write message in process log file
	 * @param message
	 */
	@Override
	public void log(String message, Throwable throwable) {
		
		logCondition(LOG_PROCESS, message, null);
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
