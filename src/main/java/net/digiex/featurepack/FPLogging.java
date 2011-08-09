package net.digiex.featurepack;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FPLogging {
	private final Logger logger = Logger.getLogger("Minecraft");

	public void info(String s) {
		logger.log(Level.INFO, "[FeaturePack] " + s);
	}

	public void debug(String s) {
		if (FPSettings.DebugMode)
			logger.log(Level.INFO, "[FeaturePack Debug] " + s);
	}

	public void severe(String s) {
		logger.log(Level.SEVERE, "[FeaturePack] " + s);
	}

	public void warning(String s) {
		logger.log(Level.WARNING, "[FeaturePack] " + s);
	}

	public void setFilter() {
		new Filter() {
			@Override
			public boolean isLoggable(LogRecord record) {
				return (record.getMessage() == null)
						|| (!record.getMessage().contains("overloaded?"))
						|| (record.getLevel() != Level.WARNING);
			}
		};
	}
}