package bwyap.utility.logging;

/**
 * Logging methods
 * @author bwyap
 *
 */
public interface LoggerInterface {
	
	/**
	 * Print a plain text message
	 * @param msg
	 */
	public void printMessage(String msg);
	
	/**
	 * Log a message
	 * @param msg
	 */
	public void logMessage(String msg);
	
	/**
	 * Log an info message
	 * @param msg
	 */
	public void logInfo(String msg);
	
	/**
	 * Log an error message
	 * @param msg
	 */
	public void logErr(String msg);
	
	/**
	 * Log a warning message
	 * @param msg
	 */
	public void logWarning(String msg);
	
}
