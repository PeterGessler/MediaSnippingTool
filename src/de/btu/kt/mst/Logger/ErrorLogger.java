package de.btu.kt.mst.Logger;

//TODO: description
public class ErrorLogger extends ALogger {
	
	private static ErrorLogger singleton;

	public static ErrorLogger getInstance() {

		if (singleton == null)
			return singleton;

		return new ErrorLogger();
	}

	private ErrorLogger() {

		logName = "ErrorLog_" + System.currentTimeMillis();
		
		//TODO: create file to safe error log and store process 
	}
	
	/**
	 * Write exception in log file
	 * 
	 * @param message
	 * @param throwable
	 */
	@Override
	public void log(String message, Throwable throwable) {
		
		logCondition(LOG_ERROR, message, throwable);
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
