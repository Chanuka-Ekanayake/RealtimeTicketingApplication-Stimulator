package org.example.ticketingapplication.util;

/**
 - Manages ticket pool and sales.

 */


import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SystemLogger {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogger.class);

    // Log an info message
    public void logInfo(String message) {
        try {
            logger.info(message);
        }catch (NullPointerException npe) {
            System.out.println("-");
        }
    }

    // Log a warning message
    public void logWarning(String message) {
        logger.warn(message);
    }

    // Log an error message
    public void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    // Log a debug message
    public void logDebug(String message) {
        logger.debug(message);
    }
}

