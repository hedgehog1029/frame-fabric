package io.github.hedgehog1029.frame.fabric;

import io.github.hedgehog1029.frame.logging.ILogReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.logging.Level;

public class FabricLogReceiver implements ILogReceiver {
	private Logger logger;

	public FabricLogReceiver(String modId) {
		logger = LogManager.getLogger(modId);
	}

	@Override
	public void printLog(Level logLevel, String message) {
		logger.log(convert(logLevel), message);
	}

	public static org.apache.logging.log4j.Level convert(Level level) {
		if (level.equals(Level.SEVERE)) return org.apache.logging.log4j.Level.FATAL;
		if (level.equals(Level.WARNING)) return org.apache.logging.log4j.Level.WARN;
		if (level.equals(Level.INFO)) return org.apache.logging.log4j.Level.INFO;
		if (level.equals(Level.FINE)) return org.apache.logging.log4j.Level.DEBUG;
		if (level.equals(Level.FINER)) return org.apache.logging.log4j.Level.TRACE;
		if (level.equals(Level.FINEST)) return org.apache.logging.log4j.Level.TRACE;

		return org.apache.logging.log4j.Level.INFO;
	}
}
