package org.metaborg.util.log;

import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nullable;

public class MetaborgLogger extends AbstractLogger {
    private final Logger logger;


    public MetaborgLogger(Logger logger) {
        this.logger = logger;
    }

    @Override public void trace(String msg, @Nullable Throwable t) {
        logger.trace(msg, t);
    }

    @Override public boolean traceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override public void debug(String msg, @Nullable Throwable t) {
        logger.debug(msg, t);
    }

    @Override public boolean debugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override public void info(String msg, @Nullable Throwable t) {
        logger.info(msg, t);
    }

    @Override public boolean infoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override public void warn(String msg, @Nullable Throwable t) {
        logger.warn(msg, t);
    }

    @Override public boolean warnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override public void error(String msg, @Nullable Throwable t) {
        logger.error(msg, t);
    }

    @Override public boolean errorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override public String format(String fmt, Object... args) {
        return MessageFormatter.arrayFormat(fmt, args).getMessage();
    }
}
