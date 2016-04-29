package de.btu.kt.mst.Logger;

//TODO: description
public abstract class ALogger {

	protected String logName;
	
	// Log states
	protected final char LOG_INFO = ':';

	protected final char LOG_ERROR = '!';

	protected final char LOG_PROCESS = '.';

	// console output state
	private static VerboseState verbose = VerboseState.INVISIBLE;
	
	/**
	 * Call method from external class to log information.
	 * 
	 * @param message
	 * @param throwable
	 */
	public abstract void log(String message, Throwable throwable);
	
	/**
	 * Write logic in concrete Logger  
	 * 
	 * @param message
	 * @param throwable
	 */
	protected abstract void logError(String message, Throwable throwable);
	
	/**
	 * Write logic in concrete Logger.
	 * 
	 * @param message
	 */
	protected abstract void logMessage(String message);

	public void setVerboseLevel(VerboseState state) {
		this.verbose = state; 
	}
	
	//TODO: description, create consistent logic 
	protected void logCondition(char type, String message, Throwable throwable) {
		
		if (verbose == VerboseState.INVISIBLE)
			return;
		if (verbose == VerboseState.VISIBLEALL && throwable != null)
			message = message + " (" + throwable.getClass().getName() + ") ";
		if (message == null)
			System.out.println("");
		if (type == LOG_ERROR)
			if (verbose == VerboseState.VISIBLEALL && throwable != null)
				logError(message, throwable);
			else
				logMessage(message);
		else if (type == LOG_INFO)
			if (verbose == VerboseState.VISIBLEINFO && throwable == null)
			{
				logMessage(message);
			}
			else {}
		else
			if (verbose == VerboseState.VISIBLEALL && throwable == null)
			{
				logMessage(message);
			}
				
	}
	
}
