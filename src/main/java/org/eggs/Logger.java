package org.eggs;

import java.util.logging.Level;

public class Logger {

    private final java.util.logging.Logger log;

    private Logger(String name) {
        log = java.util.logging.Logger.getLogger(name);
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    public void debug(String msg, Object... args) {
        log.log(Level.FINEST, msg, args);
    }

    public void debug(String msg, Throwable throwable) {
        log.log(Level.FINEST, msg, throwable);
    }

    public void info(String msg, Object... args) {
        log.log(Level.INFO, msg, args);
    }

    public void info(String msg, Throwable throwable) {
        log.log(Level.INFO, msg, throwable);
    }

    public void warn(String msg, Object... args) {
        log.log(Level.WARNING, msg, args);
    }

    public void warn(String msg, Throwable throwable) {
        log.log(Level.WARNING, msg, throwable);
    }

    public void error(String msg, Object... args) {
        log.log(Level.SEVERE, msg, args);
    }

    public void error(String msg, Throwable throwable) {
        log.log(Level.SEVERE, msg, throwable);
    }

    public static final Logger LOG = Logger.getLogger("global");
}
