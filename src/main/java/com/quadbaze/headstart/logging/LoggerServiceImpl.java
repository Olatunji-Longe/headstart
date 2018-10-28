package com.quadbaze.headstart.logging;

/**
 * Created by Olatunji O. Longe on 10/16/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements LoggerService {

    private Logger logger;

    public LoggerServiceImpl() {
        logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Throwable ex) {
        logger.error(message, ex);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void warn(String message, Throwable ex) {
        logger.warn(message, ex);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Throwable ex) {
        logger.info(message, ex);
    }

    public void debug(String message) {
        logger.info(message);
    }

    public void debug(String message, Throwable ex) {
        logger.info(message, ex);
    }

    public void trace(String message) {
        logger.trace(message);
    }

    public void trace(String message, Throwable ex) {
        logger.trace(message, ex);
    }

    public Logger getLogger(){
        return this.logger;
    }
}
